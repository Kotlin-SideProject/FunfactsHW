package com.tom.funfacts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.employee_row.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.net.URL

class QuizListFragment private constructor() : Fragment() {
    companion object {
        val instance by lazy {
            QuizListFragment()
        }

        val TAG = QuizListFragment.javaClass.simpleName
    }

    private lateinit var employeeInfos: MutableList<EmployeeInfo>
    lateinit var recycler : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_quiz_list, container, false)
        recycler = view.findViewById<RecyclerView>(R.id.recycler)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //parseJson()
        CoroutineScope(Dispatchers.IO).launch {
            // FileNotFoundException
               /* val json2 = URL("http://dummy.restapiexample.com/api/v1/employees")
                    .readText()
                Log.d(TAG, ":$json2 ")*/
            // R.raw.sample.json read(not URL)
            val json = resources.openRawResource(R.raw.sample)
                .bufferedReader()
                .readText()
            Log.d(TAG, "json: $json");
            parseJson(json)
        }

        //employee adapter setting
        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(context)
        var adapter = EmployeeAdapter()
       recycler.adapter = adapter
    }

    inner class EmployeeAdapter : RecyclerView.Adapter<EmployeeViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
            val view =  layoutInflater.inflate(R.layout.employee_row, parent, false)
            return EmployeeViewHolder(view)
        }

        override fun getItemCount(): Int {
            return employeeInfos.size
        }

        override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
            holder.id.text = employeeInfos[position].id.toString()
            holder.name.text = employeeInfos[position].name
            holder.salary.text = employeeInfos[position].salary.toString()
            holder.age.text = employeeInfos[position].age.toString()
        }

    }

    class EmployeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val id  = itemView.employee_id
        val name  = itemView.employee_name
        val salary = itemView.employee_salary
        val age  = itemView.employee_age
    }

    private fun parseJson(json: String) {
        val info = JSONArray(json)

//        val bag = listOf<Question>()
        employeeInfos = mutableListOf<EmployeeInfo>()
        for (i in 0..info.length() - 1) {
            val obj = info.getJSONObject(i)
            val id = obj.getInt("id")
            val name = obj.getString("employee_name")
            val salary = obj.getInt("employee_salary")
            val age = obj.getInt("employee_age")
            val employeeInfo = EmployeeInfo(id, name, salary, age)
            employeeInfos.add(employeeInfo)
        }
        Log.d(TAG, "JSONArray: $employeeInfos ");
    }
}