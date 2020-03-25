package org.d3if4127.jurnal08.update

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import org.d3if4127.jurnal08.R
import org.d3if4127.jurnal08.data.DiaryViewModel
import org.d3if4127.jurnal08.data.DiaryViewModelFactory
import org.d3if4127.jurnal08.database.DiaryDatabase
import org.d3if4127.jurnal08.databinding.FragmentUpdateBinding
import org.d3if4127.jurnal08.databinding.FragmentWriteBinding

/**
 * A simple [Fragment] subclass.
 */
class updateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private lateinit var diaryVM: DiaryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val id = arguments?.getLong("id")
        val content = arguments?.getString("content").toString()
        Log.i("tesAr", id.toString()+"/"+content)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = DiaryDatabase.getInstance(application).DiaryDao
        val viewModelFactory = DiaryViewModelFactory(dataSource, application)
        diaryVM = ViewModelProviders.of(this, viewModelFactory).get(DiaryViewModel::class.java)
        binding.setLifecycleOwner(this)

        binding.inputDiary.setText(content)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.updateDiary -> updateDiary()
            R.id.hapusDiari -> deleteDiary()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteDiary() {
        try {
            val id = arguments?.getLong("id")
            if (id != null) {
                diaryVM.onDelete(id = id)
            }
            Toast.makeText(this.context, "Berhasil menghapus diary", Toast.LENGTH_SHORT).show()
            this.view?.findNavController()?.navigate(R.id.action_updateFragment_to_dashboardFragment)
        }catch (e: Exception) {
            Toast.makeText(this.context, "Data tidak valid", Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateDiary() {
        try {
            val id = arguments?.getLong("id")
            var content = binding.inputDiary.text.toString()
            if (id != null) {
                diaryVM.onUpdate(id = id, newContent = binding.inputDiary.text.toString())
            }
            Toast.makeText(this.context, "Berhasil update diary", Toast.LENGTH_SHORT).show()
            this.view?.findNavController()?.navigate(R.id.action_updateFragment_to_dashboardFragment)
        }catch (e: Exception) {
            Toast.makeText(this.context, "Data tidak valid", Toast.LENGTH_SHORT).show()
        }
    }
}
