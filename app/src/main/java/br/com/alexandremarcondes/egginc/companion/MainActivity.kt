package br.com.alexandremarcondes.egginc.companion

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.tooling.preview.Preview
import br.com.alexandremarcondes.egginc.companion.ui.EggIncCompanionTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EggIncCompanionTheme {
                Greeting("Android")
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EggIncCompanionTheme {
        Greeting("Android")
    }
}