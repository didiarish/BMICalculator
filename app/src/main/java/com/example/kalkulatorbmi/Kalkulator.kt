package com.example.kalkulatorbmi


import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.kalkulatorbmi.databinding.FragmentKalkulatorBinding
import kotlinx.android.synthetic.main.fragment_kalkulator.*
import java.lang.NumberFormatException

/**
 * A simple [Fragment] subclass.
 */
class Kalkulator : Fragment() {

    private var hasilBMI = ""
    private var grade = ""
    private var color = 0
    private var saveBB = 0.0
    private var saveTB = 0.0
    private var gender = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentKalkulatorBinding>(
            inflater,
            R.layout.fragment_kalkulator,
            container,
            false
        )

        if (savedInstanceState != null) {
            hasilBMI = savedInstanceState.getString("bmi").toString()
            grade = savedInstanceState.getString("grade").toString()
            color = savedInstanceState.getInt("color")

            binding.textView.text = hasilBMI
            binding.textView2.text = grade
            binding.textView2.setTextColor(color)
        }


        binding.button.setOnClickListener {

            try {
                var bb = binding.editText.text.toString().toDouble()
                var tb = binding.editText2.text.toString().toDouble()

                saveBB = bb
                saveTB = tb

                var hasil = bb / Math.pow(tb / 100, 2.0)

                hasilBMI = String.format("%.1f", hasil)

                if (binding.rbPria.isChecked) {
                    binding.button2.visibility = View.VISIBLE
                    binding.button3.visibility = View.VISIBLE
                    gender = binding.rbPria.text.toString()
                    if (hasilBMI.toDouble() < 20.50) {
                        binding.textView.text = hasilBMI
                        grade = "Kurus"
                        binding.textView2.text = grade
                        color = resources.getColor(R.color.kategori_kurus)
                        binding.textView2.setTextColor(color)
                    } else if (hasilBMI.toDouble() > 20.50 && hasilBMI.toDouble() < 26.99) {
                        binding.textView.text = hasilBMI
                        grade = "Ideal"
                        binding.textView2.text = grade
                        color = resources.getColor(R.color.kategori_ideal)
                        binding.textView2.setTextColor(color)
                    } else if (hasilBMI.toDouble() > 27.00) {
                        binding.textView.text = hasilBMI
                        grade = "Gemuk"
                        binding.textView2.text = grade
                        color = resources.getColor(R.color.kategori_gemuk)
                        binding.textView2.setTextColor(color)
                    }
                } else if (binding.rbWanita.isChecked) {
                    binding.button2.visibility = View.VISIBLE
                    binding.button3.visibility = View.VISIBLE
                    gender = binding.rbWanita.text.toString()
                    if (hasilBMI.toDouble() < 18.50) {
                        binding.textView.text = hasilBMI
                        grade = "Kurus"
                        binding.textView2.text = grade
                        color = resources.getColor(R.color.kategori_kurus)
                        binding.textView2.setTextColor(color)
                    } else if (hasilBMI.toDouble() > 18.50 && hasilBMI.toDouble() < 24.99) {
                        binding.textView.text = hasilBMI
                        grade = "Ideal"
                        binding.textView2.text = grade
                        color = resources.getColor(R.color.kategori_ideal)
                        binding.textView2.setTextColor(color)
                    } else if (hasilBMI.toDouble() > 25.0) {
                        binding.textView.text = hasilBMI
                        grade = "Gemuk"
                        binding.textView2.text = grade
                        color = resources.getColor(R.color.kategori_gemuk)
                        binding.textView2.setTextColor(color)
                    }
                } else {
                    Toast.makeText(this.activity, "Belum mengisi Gender", Toast.LENGTH_LONG).show()
                }
            } catch (ex: NumberFormatException) {
                Toast.makeText(
                    this.activity,
                    "Berat dan Tinggi Badan harus diisi",
                    Toast.LENGTH_LONG
                ).show()
                binding.textView.text = null
                binding.textView2.text = null
            }
        }

        binding.button2.setOnClickListener {
            if (binding.textView2.text.equals("Kurus")) {
                view?.findNavController()?.navigate(R.id.action_kalkulator_to_kurus)
            } else if (binding.textView2.text.equals("Ideal")) {
                view?.findNavController()?.navigate(R.id.action_kalkulator_to_ideal)
            } else if (binding.textView2.text.equals("Gemuk")) {
                view?.findNavController()?.navigate(R.id.action_kalkulator_to_gemuk)
            }
        }

        binding.button3.setOnClickListener {
            onShare()
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("bmi", hasilBMI)
        outState.putString("grade", grade)
        outState.putInt("color", color)
        super.onSaveInstanceState(outState)
    }

    private fun onShare() {
        val shareIntent = ShareCompat.IntentBuilder.from(this.activity!!)
            .setText("Berat Badan = " + saveBB + "\nTinggi Badan = " + saveTB + "\nGender = " + gender +  "\nNilai BMI = " + hasilBMI + "\n" + grade)
            .setType("text/plain")
            .intent

        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                this.activity, "Share is not Available",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item!!,
            view!!.findNavController()
        ) || super.onOptionsItemSelected(item)
    }

}
