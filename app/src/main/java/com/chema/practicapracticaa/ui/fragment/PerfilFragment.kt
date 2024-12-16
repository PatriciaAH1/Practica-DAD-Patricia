package com.chema.practicapracticaa.ui.fragment

import LogoActivity
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.chema.practicapracticaa.R
import com.chema.practicapracticaa.data.api.RecetaRepositorio
import com.chema.practicapracticaa.data.bd.AppBaseDatos
import com.chema.practicapracticaa.data.bd.UsuarioRepositorio
import com.chema.practicapracticaa.databinding.FragmentPerfilBinding
import com.chema.practicapracticaa.modelo.Usuarios
import com.chema.practicapracticaa.ui.activity.BienvenidoActivity
import com.chema.practicapracticaa.ui.activity.LoginActivity

import com.chema.practicapracticaa.ui.viewmodel.RecetaViewModel
import com.chema.practicapracticaa.ui.viewmodel.UsuarioViewModel
import com.chema.practicapracticaa.ui.viewmodel.factory.RecetaViewModelFactory


class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var usuarioViewModel: UsuarioViewModel
    private var isEditing = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)

        val userDao = AppBaseDatos.obtenerBaseDatos(requireContext()).usuarioDao()
        usuarioViewModel = UsuarioViewModel(UsuarioRepositorio(userDao))

        establecerFunciones()

        return binding.root
    }

    private fun establecerFunciones() {
        cerrarSesion()
        cargarDatosUsuario()
        mostrarDatoUsuarioTextView()
        configurarBotonEditar()
    }

    private fun cargarDatosUsuario() {
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt("user_id", -1)

        if (id != -1) {
            usuarioViewModel.obtenerUsuarioPorId(id) { usuario ->
                if (usuario != null) {
                    mostrarDatosUsuarioEditText(usuario)
                } else {
                    Toast.makeText(requireContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "No se pudo cargar el usuario", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun mostrarDatoUsuarioTextView() {
        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt("user_id", -1)

        if (id != -1) {
            usuarioViewModel.obtenerUsuarioPorId(id) { usuario ->
                if (usuario != null) {
                    with(binding) {
                        textViewNombreCompleto.text = "${usuario.nombre} ${usuario.apellido}"
                        textViewEmail.text = "Email: ${usuario.email}"
                        textViewTelefono.text = "Telefono: ${usuario.telefono}"
                    }
                } else {
                    Toast.makeText(requireContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "No se pudo cargar el usuario", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarDatosUsuarioEditText(usuarios: Usuarios) {
        with(binding) {
            editTextName.setText(usuarios.nombre)
            editTextLastName.setText(usuarios.apellido)
            editTextUsername.setText(usuarios.usuario)
            editTextPhone.setText(usuarios.telefono)
            editTextEmail.setText(usuarios.email)
        }
        habilitarCampos(false)
    }

    private fun configurarBotonEditar() {
        binding.buttonEditar.setOnClickListener {
            if (!isEditing) {
                habilitarCampos(true)
                binding.buttonEditar.text = getString(R.string.actualizar)
                isEditing = true
            } else {
                actualizarInformacion()
            }
        }
    }

    private fun habilitarCampos(habilitar: Boolean) {
        with(binding) {
            editTextName.isEnabled = habilitar
            editTextLastName.isEnabled = habilitar
            editTextUsername.isEnabled = habilitar
            editTextPhone.isEnabled = habilitar
            editTextEmail.isEnabled = habilitar
        }
    }

    private fun actualizarInformacion() {
        val nuevoNombre = binding.editTextName.text.toString().trim()
        val nuevoApellido = binding.editTextLastName.text.toString().trim()
        val nuevoUsuario = binding.editTextUsername.text.toString().trim()
        val nuevoTelefono = binding.editTextPhone.text.toString().trim()
        val nuevoEmail = binding.editTextEmail.text.toString().trim()

        if (nuevoNombre.isEmpty() || nuevoApellido.isEmpty() || nuevoUsuario.isEmpty() || nuevoTelefono.isEmpty() || nuevoEmail.isEmpty()) {
            Toast.makeText(requireContext(), "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt("user_id", -1)

        if (id != -1) {
            usuarioViewModel.obtenerUsuarioPorId(id) { usuario ->
                if (usuario != null) {
                    usuario.nombre = nuevoNombre
                    usuario.apellido = nuevoApellido
                    usuario.usuario = nuevoUsuario
                    usuario.telefono = nuevoTelefono
                    usuario.email = nuevoEmail

                    usuarioViewModel.actualizarInformacion(usuario) { exito ->
                        if (exito) {
                            Toast.makeText(requireContext(), "Información actualizada correctamente", Toast.LENGTH_SHORT).show()
                            mostrarDatosUsuarioEditText(usuario)
                            mostrarDatoUsuarioTextView()
                            habilitarCampos(false)
                            binding.buttonEditar.text = getString(R.string.editar)
                            isEditing = false
                        } else {
                            Toast.makeText(requireContext(), "Error al actualizar la información", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(requireContext(), "No se pudo obtener el usuario", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cerrarSesion() {
        binding.buttonLogOut.setOnClickListener {
            val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("is_logged_in", false)
            editor.remove("user_id")
            editor.apply()

            Toast.makeText(requireContext(), "Sesión cerrada", Toast.LENGTH_SHORT).show()

            val intent = Intent(requireContext(), LogoActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}