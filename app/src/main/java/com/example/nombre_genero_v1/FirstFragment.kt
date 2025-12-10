package com.example.nombre_genero_v1

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.example.nombre_genero_v1.databinding.FragmentFirstBinding
import com.example.nombre_genero_v1.api.RetrofitClient
import com.example.nombre_genero_v1.models.GenderResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }
    private fun hideKeyboard() {
        val imm = requireActivity()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        view?.let { v ->
            imm.hideSoftInputFromWindow(v.windowToken, 0)
            v.clearFocus()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Binding directo
        binding.btnConsultar.setOnClickListener {
            hideKeyboard();
            val nombre = binding.etNombre.text.toString().trim()
            if(nombre.isEmpty())
            {
                binding.tvGenero.setText( "Nombre invalido")
            }
            else{
            RetrofitClient.api.getGender(nombre)
                .enqueue(object : Callback<GenderResponse> {
                    override fun onResponse(
                        call: Call<GenderResponse>,
                        response: Response<GenderResponse>
                    ) {
                        val body = response.body()
                        if (body == null) {
                            binding.tvGenero.setText("No hay respuesta");
                        } else {
                            if (body.gender.equals("male"))
                                binding.tvGenero.setText("Masculino")
                            else
                                binding.tvGenero.setText("Femenino")
                        }

                    }

                    override fun onFailure(call: Call<GenderResponse>, t: Throwable) {
                        binding.tvGenero.setText("Error: ${t.message}")
                    }

                })}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}