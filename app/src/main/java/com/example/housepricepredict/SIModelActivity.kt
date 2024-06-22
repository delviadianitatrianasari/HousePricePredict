package com.example.housepricepredict

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.text.DecimalFormat

class SIModelActivity : AppCompatActivity() {
    private lateinit var interpreter: Interpreter
    private val mModelPath = "houseprice.tflite"

    private lateinit var resultText : TextView
    private lateinit var bedrooms : EditText
    private lateinit var bathrooms : EditText
    private lateinit var floors : EditText
    private lateinit var sqft_living: EditText
    private lateinit var condition : EditText
    private lateinit var grade : EditText
    private lateinit var sqft_lot : EditText
    private lateinit var yr_built : EditText
    private lateinit var checkButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simodel)

        // Initialize the views
        resultText = findViewById(R.id.txtResult)
        bedrooms = findViewById(R.id.bedrooms)
        bathrooms = findViewById(R.id.bathrooms)
        floors = findViewById(R.id.floors)
        sqft_living = findViewById(R.id.sqft_living)
        condition = findViewById(R.id.condition)
        grade = findViewById(R.id.grade)
        sqft_lot = findViewById(R.id.sqft_lot)
        yr_built = findViewById(R.id.yr_built)
        checkButton = findViewById(R.id.btnCheck)

        // Load the model
        try {
            interpreter = Interpreter(loadModelFile(mModelPath))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // Tombol Kembali
        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        // Set the click listener for the button
        checkButton.setOnClickListener {
            val result = doInference(
                bedrooms.text.toString().toFloat(),
                bathrooms.text.toString().toFloat(),
                floors.text.toString().toFloat(),
                sqft_living.text.toString().toFloat(),
                condition.text.toString().toFloat(),
                grade.text.toString().toFloat(),
                sqft_lot.text.toString().toFloat(),
                yr_built.text.toString().toFloat()
            )
            runOnUiThread {
                // Convert the result to Rupiah (assuming the model returns USD)
                val exchangeRateUsdToIdr = 14000.0f // Example exchange rate
                val resultInIdr = result * exchangeRateUsdToIdr

                // Format the result to 3 digit with ' JUTA' suffix
                val formattedResult = DecimalFormat("#,###").format(resultInIdr / 1_000_000) + " JUTA"

                // Display the formatted result
                resultText.text = "Harga: Rp.$formattedResult"
            }
        }
    }

    @Throws(IOException::class)
    private fun loadModelFile(modelPath: String): MappedByteBuffer {
        val fileDescriptor = assets.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun doInference(
        bedrooms: Float,
        bathrooms: Float,
        floors: Float,
        sqft_living: Float,
        condition: Float,
        grade: Float,
        sqft_lot: Float,
        yr_built: Float
    ): Float {
        val input = arrayOf(
            floatArrayOf(
                bedrooms,
                bathrooms,
                floors,
                sqft_living,
                condition,
                grade,
                sqft_lot,
                yr_built
            )
        )
        val output = Array(1) { FloatArray(1) }
        interpreter.run(input, output)
        return output[0][0]
    }
}
