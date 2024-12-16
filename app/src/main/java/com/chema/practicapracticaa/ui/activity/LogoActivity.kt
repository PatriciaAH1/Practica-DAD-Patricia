import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.chema.practicapracticaa.databinding.ActivityLogoBinding
import com.chema.practicapracticaa.ui.activity.BienvenidoActivity

class LogoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarAnimacionClick()
        ocultarBarraDeEstadoEnSplashScreen()
    }

    private fun configurarAnimacionClick() {
        binding.imageViewLogo.setOnClickListener {
            // Animación de rotación
            val animator = ObjectAnimator.ofFloat(it, "rotation", 0f, 360f)
            animator.duration = 1000 // Duración de la animación (1 segundo)
            animator.interpolator = DecelerateInterpolator()

            animator.addListener(object : android.animation.Animator.AnimatorListener {
                override fun onAnimationStart(animation: android.animation.Animator) {}
                override fun onAnimationEnd(animation: android.animation.Animator) {
                    // Al finalizar la animación, inicia la nueva actividad
                    startActivity(Intent(this@LogoActivity, BienvenidoActivity::class.java))
                    finish()
                }
                override fun onAnimationCancel(animation: android.animation.Animator) {}
                override fun onAnimationRepeat(animation: android.animation.Animator) {}
            })

            animator.start()
        }
    }

    private fun ocultarBarraDeEstadoEnSplashScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }
}
