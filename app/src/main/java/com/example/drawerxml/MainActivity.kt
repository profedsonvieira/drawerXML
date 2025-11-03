package com.example.drawerxml

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar componentes
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)

        // Configurar a Toolbar
        setSupportActionBar(toolbar)

        // Configurar o toggle do drawer
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Configurar o listener do navigation view
        navigationView.setNavigationItemSelectedListener(this)

        // Definir item inicial selecionado
        navigationView.setCheckedItem(R.id.nav_home)

        // Configurar o novo back press handler
        setupBackPressedHandler()
    }

    private fun setupBackPressedHandler() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    // Se você quiser comportamento padrão (fechar app)
                    // isEnabled = false
                    // onBackPressedDispatcher.onBackPressed()

                    // Ou comportamento customizado
                    if (shouldCloseAppOnBackPress()) {
                        finish()
                    } else {
                        // Navegação dentro do app
                        handleCustomBackNavigation()
                    }
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    /**
     * Define quando o app deve fechar ao pressionar voltar
     * Por exemplo, se estiver na tela inicial, fecha o app
     */
    private fun shouldCloseAppOnBackPress(): Boolean {
        // Implemente sua lógica aqui
        // Por exemplo, verificar se está na tela principal
        return navigationView.checkedItem?.itemId == R.id.nav_home
    }

    /**
     * Lógica customizada para navegação com botão voltar
     */
    private fun handleCustomBackNavigation() {
        // Exemplo: voltar para a tela home
        navigationView.setCheckedItem(R.id.nav_home)
        showMessage("Voltando para Home")
        // Ou implemente sua própria lógica de navegação
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {
            R.id.nav_home -> showMessage("Home selecionado")
            R.id.nav_profile -> showMessage("Perfil selecionado")
            R.id.nav_settings -> showMessage("Configurações selecionadas")
            R.id.nav_help -> showMessage("Ajuda selecionada")
            R.id.nav_about -> showMessage("Sobre selecionado")
        }

        // Fechar o drawer após a seleção
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}