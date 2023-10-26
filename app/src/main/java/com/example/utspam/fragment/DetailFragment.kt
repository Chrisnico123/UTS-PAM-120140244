import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.utspam.R
import com.example.utspam.model.DataItem

class DetailFragment : Fragment() {
    private lateinit var dataItem: DataItem

    fun setDataItem(dataItem: DataItem) {
        this.dataItem = dataItem
        val textName = view?.findViewById<TextView>(R.id.text_detail)
        textName?.text = dataItem.email
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }
}