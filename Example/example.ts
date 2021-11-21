import { readFileSync, writeFileSync } from "fs"
import P from "pino"
import { Boom } from "@hapi/boom"
import makeWASocket, { AuthenticationState, DisconnectReason, AnyMessageContent, BufferJSON, initInMemoryKeyStore, delay } from '../src'

// load authentication state from a file
const loadState = () => {
    let state: AuthenticationState | undefined = undefined
    try {
        const value = JSON.parse(
            readFileSync('./auth_info_multi.json', { encoding: 'utf-8' }), 
            BufferJSON.reviver
        )
        state = { 
            creds: value.creds, 
            // stores pre-keys, session & other keys in a JSON object
            // we deserialize it here
            keys: initInMemoryKeyStore(value.keys) 
        }
    } catch{  }
    return state
}
// save the authentication state to a file
const saveState = (state: any | undefined) => {
    console.log('saving auth state')
    writeFileSync(
        './auth_info_multi.json', 
        // BufferJSON replacer utility saves buffers nicely
        JSON.stringify(state, BufferJSON.replacer, 2)
    )
}

// start a connection
const startSock = () => {
    const sock = makeWASocket({
        printQRInTerminal: true,
        auth: loadState()
    })

    const sendMessageWTyping = async(msg: AnyMessageContent, jid: string) => {
        await sock.presenceSubscribe(jid)
        await delay(500)

        await sock.sendPresenceUpdate('composing', jid)
        await delay(2000)

        await sock.sendPresenceUpdate('paused', jid)

        await sock.sendMessage(jid, msg)
    }
    
	sock.ev.on('messages.upsert', ({ messages, type }) => {
		console.log('got messages')
		console.log(messages);
		console.log(type);
	})

    sock.ev.on('connection.update', (update) => {
        const { connection, lastDisconnect } = update
        if(connection === 'close') {
            // reconnect if not logged out
            if((lastDisconnect.error as Boom)?.output?.statusCode !== DisconnectReason.loggedOut) {
                startSock()
            } else {
                console.log('connection closed')
            }
        }
        
        console.log('connection update', update)
    })
	
	
    // listen for when the auth state is updated
    // it is imperative you save this data, it affects the signing keys you need to have conversations
    sock.ev.on('auth-state.update', () => saveState(sock.authState))

    return sock
}

startSock()