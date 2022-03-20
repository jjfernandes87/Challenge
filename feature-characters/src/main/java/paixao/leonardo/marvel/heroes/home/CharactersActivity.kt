package paixao.leonardo.marvel.heroes.home

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import paixao.leonardo.marvel.heroes.home.databinding.CharactersActivityBinding

class CharactersActivity : AppCompatActivity() {

    private lateinit var viewBinding: CharactersActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = CharactersActivityBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}