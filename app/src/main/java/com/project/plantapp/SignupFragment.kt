package com.project.plantapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.project.plantapp.databinding.FragmentSignupBinding
import com.project.plantapp.viewmodel.UserVM

class SignupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private lateinit var viewModel: UserVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[UserVM::class.java]
        binding.signInBnt.setOnClickListener {
            val firstName = binding.signUpFirstName.text.toString()
            val lastName = binding.signUpLastName.text.toString()
            val email = binding.signUpEmail.text.toString()
            val password = binding.signUpPassword.text.toString()
            val confirmPassword = binding.signUpConfirmPassword.text.toString()

            viewModel.createUserWithEmailAndPassword(email, password, confirmPassword)



            listenerSuccessEvent()
            listenerErrorEvent()
        }

        return binding.root
    }


    private fun listenerSuccessEvent() {
        viewModel.isSuccessEvent.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                findNavController().popBackStack()
            }
        }
    }

    private fun listenerErrorEvent() {
        viewModel.isMessageEvent.observe(viewLifecycleOwner) { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }
}