package com.project.plantapp

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.project.plantapp.databinding.FragmentLoginBinding
import com.project.plantapp.viewmodel.UserVM


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: UserVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[UserVM::class.java]
        binding.signUpBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
        binding.forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }


        binding.signInBnt.setOnClickListener{
            viewModel.signInWithEmailAndPassword(binding.loginEmail.text.toString().trim(),binding.loginPassword.text.toString().trim())
        }

        listenerSuccessEvent()
        listenerErrorEvent()

        return binding.root
    }


    private fun listenerSuccessEvent() {
        viewModel.isSuccessEvent.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                findNavController().navigate(R.id.homeFragment)
            }
        }
    }

    private fun listenerErrorEvent() {
        viewModel.isMessageEvent.observe(viewLifecycleOwner) { errMsg ->
            Toast.makeText(context, errMsg, Toast.LENGTH_SHORT).show()
        }
    }


}