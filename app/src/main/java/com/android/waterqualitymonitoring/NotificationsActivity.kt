package com.android.waterqualitymonitoring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.google.firebase.database.*
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NotificationsActivity : AppCompatActivity() {
    var temperature = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_notifications)
        FirebaseApp.initializeApp(this)

        lifecycleScope.launch {
            temperature = getSensorData("Temperature").toString()
            findViewById<TextView>(R.id.temp_value).setText("$temperature °C")
        }
    }

    private suspend fun getSensorData(element: String): Int = suspendCoroutine { continuation ->
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("Water")
        val elementReference = databaseReference.child(element)

        elementReference.orderByKey().limitToLast(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Check if there is any data
                    if (dataSnapshot.exists()) {
                        // Iterate through the dataSnapshot to get the last entry
                        for (childSnapshot in dataSnapshot.children) {
                            val lastValue = childSnapshot.getValue(Int::class.java)
                            continuation.resume(lastValue ?: 0)
                            return
                        }
                    }
                    continuation.resume(0)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("Failed to retrieve last $element value: ${databaseError.message}")
                    continuation.resume(0)
                }
            })
    }
}