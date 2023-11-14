package com.example.livewellrestructured.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.livewellrestructured.data.openSans

@Composable
fun ContactUsScreen(modifier: Modifier = Modifier) {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        item {
            Spacer(modifier.height(48.dp))
        }
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    "Contact Us",
                    fontFamily = openSans,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Spacer(modifier.height(4.dp))
                Text(
                    """There are so many ways to support our work. Contact us to find out more about volunteer opportunities, fundraising events, and ways to get our message to your friends and family.
If you would like to meet us in person, send a message with your contact details and we can arrange for our Community Developer to get in touch.""",
                    fontFamily = openSans,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
        item {
            Column() {
                Spacer(modifier.height(28.dp))
                Text("Contact us:", fontWeight = FontWeight.SemiBold)
                Spacer(modifier.height(16.dp))
                Text("Community Developer", fontWeight = FontWeight.SemiBold)

                val context = LocalContext.current
                Button(onClick = {
                    context.sendMail("connect@livewellinbraunton.co.uk", "")
                }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Rounded.Email, contentDescription = "Email")
                        Spacer(modifier.width(6.dp))
                        Text("connect@livewellinbraunton.co.uk")
                    }
                }

                Spacer(modifier.height(8.dp))

                Text("Marketing, Admin & Volunteer Co-Ordinator", fontWeight = FontWeight.SemiBold)

                Button(
                    onClick = {
                        context.sendMail("support@livewellinbraunton.co.uk", "")
                    },
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Rounded.Email, contentDescription = "Email")
                        Spacer(modifier.width(6.dp))
                        Text("support@livewellinbraunton.co.uk")
                    }
                }
            }
        }
    }
}

// Contact Us helper functions
fun Context.sendMail(to: String, subject: String) {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "vnd.android.cursor.item/email" // or "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        // No email app available
        Toast.makeText(this, "No email app available.", Toast.LENGTH_SHORT).show()
    } catch (t: Throwable) {
        // Other exception
        Toast.makeText(this, "An error occurred.", Toast.LENGTH_SHORT).show()
    }
}