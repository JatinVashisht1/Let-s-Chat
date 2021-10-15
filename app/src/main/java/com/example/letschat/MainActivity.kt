package com.example.letschat

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.letschat.ui.theme.LetsChatTheme
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.client.models.User
import io.getstream.chat.android.compose.ui.channel.ChannelsScreen
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.livedata.ChatDomain

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1 - Set up the client for API calls and the domain for offline storage
        val client = ChatClient.Builder("udpka8kvp2kq", applicationContext)
            .logLevel(ChatLogLevel.ALL) // Set to NOTHING in prod
            .build()
        ChatDomain.Builder(client, applicationContext).build()

        // 2 - Authenticate and connect the user
        val user = User(
            id = "tutorial-droid",
            extraData = mutableMapOf(
                "name" to "Tutorial Droid",
                "image" to "https://bit.ly/2TIt8NR",
            ),
        )
        client.connectUser(
            user = user,
            token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoidHV0b3JpYWwtZHJvaWQifQ.NhEr0hP9W9nwqV7ZkdShxvi02C5PR7SJE7Cs4y7kyqg"
        ).enqueue()

        // 3 - Set up the Channels Screen UI
        setContent {
            LetsChatTheme() {
                Surface(color = MaterialTheme.colors.background) {
                    ChatTheme {
                        ChannelsScreen(
                            title = stringResource(id = R.string.app_name),
                            onItemClick = { channel ->
                                Log.d("HomeScreen", channel.toString())
                                startActivity(MessagesActivity.getIntent(this, channel.cid))
//                                Toast.makeText(this, channel.toString(), Toast.LENGTH_SHORT).show()
                            },
                            onBackPressed = { finish() }
                        )
                    }
                }
            }
        }
    }
}