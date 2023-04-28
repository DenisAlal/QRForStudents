package com.denisal.qrforstudents.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denisal.qrforstudents.R
import com.denisal.qrforstudents.pass
import com.denisal.qrforstudents.url
import com.denisal.qrforstudents.user
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class GradeFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_grade, container, false)
        Thread {
            getData(view)
        }.start()
        return view

    }
    private fun getData(view: View) {
        activity?.runOnUiThread {loading(view)}
        val mPrefs: SharedPreferences = activity?.getSharedPreferences("data", 0)!!
        val fullName = mPrefs.getString("fullName", "")
        val group = mPrefs.getString("group", "")
        Log.e("12312312312312", "$fullName,   $group")
        val array: MutableList<GradeModel> = arrayListOf()

            try {
                Class.forName("com.mysql.jdbc.Driver")
                val cn: Connection = DriverManager.getConnection(url, user, pass)
                val sql = "SELECT course.name, lesson.name, task.name, assessment.date, " +
                        "assessment.value FROM assessment INNER JOIN student ON student_id = student.id " +
                        "INNER JOIN task ON task_id = task.id INNER JOIN lesson ON lesson_id = lesson.id " +
                        "INNER JOIN course ON task.course_id = course.id WHERE studGroup = '$group' AND " +
                        "fullName= '$fullName' ORDER BY course.name ASC"
                val query = cn.prepareStatement(sql)
                val result = query.executeQuery()


                        while (result.next()) {
                    var courseName = result.getString(1)
                    var lessonName = result.getString(2)
                    var taskName = result.getString(3)
                    var date = result.getString(4)
                    var valueGrade = result.getString(5)

                    array.add(GradeModel(courseName, lessonName, taskName, date, valueGrade,))
                }
                cn.close()

            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: SQLException) {
                e.printStackTrace()
            }


        activity?.runOnUiThread {
            finishLoad(array, view)
        }

    }
    private fun loading(view: View) {
        val processLoading: ProgressBar = view.findViewById(R.id.processLoading)
        processLoading.isVisible = true
    }
    private fun finishLoad(array: MutableList<GradeModel>, view: View) {
        val processLoading: ProgressBar = view.findViewById(R.id.processLoading)
        processLoading.isVisible = false
        val recyclerViewMovieList = view.findViewById<RecyclerView>(R.id.recyclerViewMovieList)
        if (recyclerViewMovieList != null) {
            recyclerViewMovieList.layoutManager = LinearLayoutManager(requireContext())
            recyclerViewMovieList.adapter = ViewAdapter(array)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            GradeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}