package com.rafayet.pilescare

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val HomeBg = Color(0xFFF4F8FC)
private val Navy = Color(0xFF12345A)
private val Blue = Color(0xFF1687E8)
private val Green = Color(0xFF16A875)
private val SoftBlue = Color(0xFFE3F3FF)
private val SoftGreen = Color(0xFFE5F8EF)
private val SoftOrange = Color(0xFFFFF0DD)
private val SoftPurple = Color(0xFFF1E9FF)
private val SubText = Color(0xFF718096)

@Composable
fun ModernHomeScreen(
    water: Int,
    onAddWater: () -> Unit,
    stool: String,
    onSelectStool: (String) -> Unit,
    burning: Int,
    onBurningChange: (Int) -> Unit
) {
    var vegetables by remember { mutableStateOf(false) }
    var fruits by remember { mutableStateOf(false) }
    var fiber by remember { mutableStateOf(false) }

    val progress = (water / 3000f).coerceIn(0f, 1f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeBg)
            .verticalScroll(rememberScrollState())
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text("Piles Care 🌿", color = Navy, fontSize = 27.sp, fontWeight = FontWeight.ExtraBold)
                Text("আপনার প্রতিদিনের যত্নের সঙ্গী", color = SubText, fontSize = 13.sp)
                Text("Your daily health companion", color = SubText, fontSize = 12.sp)
            }
            Surface(shape = CircleShape, color = Color.White, shadowElevation = 4.dp) {
                Text("🔔", modifier = Modifier.padding(13.dp), fontSize = 21.sp)
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {
            Row(
                modifier = Modifier.padding(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(shape = CircleShape, color = SoftGreen, modifier = Modifier.size(58.dp)) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("🌱", fontSize = 29.sp)
                    }
                }
                Spacer(Modifier.width(13.dp))
                Column {
                    Text("Welcome Back!", color = SubText, fontSize = 13.sp)
                    Text("আজ নিজের যত্ন নিন", color = Navy, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text("Take care of your health 💚", color = SubText, fontSize = 12.sp)
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("💧 Water Tracking / পানি ট্র্যাকিং", color = Navy, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(16.dp))

                Box(contentAlignment = Alignment.Center, modifier = Modifier.size(166.dp)) {
                    CircularProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxSize(),
                        color = Blue,
                        trackColor = SoftBlue,
                        strokeWidth = 13.dp
                    )
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🥤", fontSize = 34.sp)
                        Text("$water", color = Navy, fontSize = 27.sp, fontWeight = FontWeight.ExtraBold)
                        Text("ml / 3000 ml", color = SubText, fontSize = 12.sp)
                    }
                }

                Spacer(Modifier.height(10.dp))
                Text("${(progress * 100).toInt()}% সম্পন্ন", color = Green, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = onAddWater,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Blue)
                ) {
                    Text("+ Add 250 ml পানি", fontWeight = FontWeight.Bold)
                }
            }
        }

        Text("🥗 Food Tracking / খাবার ট্র্যাকিং", color = Navy, fontSize = 17.sp, fontWeight = FontWeight.Bold)

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("আজ কী কী খেয়েছেন?", color = Navy, fontWeight = FontWeight.Bold)
                Text("What did you eat today?", color = SubText, fontSize = 12.sp)
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    FoodOption("🥬 সবজি", vegetables, { vegetables = it }, Modifier.weight(1f))
                    FoodOption("🍎 ফল", fruits, { fruits = it }, Modifier.weight(1f))
                    FoodOption("🌾 আঁশ", fiber, { fiber = it }, Modifier.weight(1f))
                }
            }
        }

        Text("🚽 Stool Tracking / পায়খানা ট্র্যাকিং", color = Navy, fontSize = 17.sp, fontWeight = FontWeight.Bold)

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("আজ পায়খানা কেমন ছিল?", color = Navy, fontWeight = FontWeight.Bold)
                Text("How was your stool today?", color = SubText, fontSize = 12.sp)
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    listOf("Soft", "Normal", "Hard").forEach { option ->
                        FilterChip(
                            selected = stool == option,
                            onClick = { onSelectStool(option) },
                            label = {
                                Text(
                                    when (option) {
                                        "Soft" -> "নরম"
                                        "Normal" -> "স্বাভাবিক"
                                        else -> "শক্ত"
                                    }
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = SoftGreen,
                                selectedLabelColor = Green
                            )
                        )
                    }
                }
                Text("Selected: $stool", color = Green, fontSize = 12.sp)
            }
        }

        Text("❤️ Symptom Tracking / উপসর্গ", color = Navy, fontSize = 17.sp, fontWeight = FontWeight.Bold)

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(Modifier.padding(16.dp)) {
                Text("জ্বালাপোড়া কতটা?", color = Navy, fontWeight = FontWeight.Bold)
                Text("Burning level: $burning / 10", color = Blue, fontSize = 17.sp)
                Slider(
                    value = burning.toFloat(),
                    onValueChange = { onBurningChange(it.toInt()) },
                    valueRange = 0f..10f,
                    steps = 9
                )
                Text("০ = নেই     ১০ = খুব বেশি", color = SubText, fontSize = 12.sp)
            }
        }

        Text("✨ Your Care / আপনার যত্ন", color = Navy, fontSize = 17.sp, fontWeight = FontWeight.Bold)

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            FeatureCard("💊", "Medicine", "ওষুধের রিমাইন্ডার", SoftPurple, Modifier.weight(1f))
            FeatureCard("📊", "Daily Report", "দৈনিক রিপোর্ট", SoftBlue, Modifier.weight(1f))
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            FeatureCard("🛁", "Sitz Bath", "গরম পানিতে বসা", SoftOrange, Modifier.weight(1f))
            FeatureCard("📚", "Care Guide", "যত্নের পরামর্শ", SoftGreen, Modifier.weight(1f))
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF6D9))
        ) {
            Text(
                "💡 স্বাস্থ্য টিপস\nপর্যাপ্ত পানি পান করুন, আঁশযুক্ত খাবার খান এবং পায়খানার সময় অতিরিক্ত চাপ দেবেন না।",
                modifier = Modifier.padding(15.dp),
                color = Navy,
                fontSize = 13.sp,
                lineHeight = 20.sp
            )
        }

        Text(
            "নোট: এই অ্যাপ স্বাস্থ্য তথ্য ট্র্যাক করার জন্য; এটি রোগ নির্ণয় বা চিকিৎসকের বিকল্প নয়।",
            color = SubText,
            fontSize = 11.sp,
            modifier = Modifier.padding(bottom = 12.dp)
        )
    }
}

@Composable
private fun FoodOption(
    label: String,
    selected: Boolean,
    onSelected: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        modifier = modifier,
        selected = selected,
        onClick = { onSelected(!selected) },
        label = { Text(label, fontSize = 11.sp) },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = SoftGreen,
            selectedLabelColor = Green
        )
    )
}

@Composable
private fun FeatureCard(
    emoji: String,
    title: String,
    subtitle: String,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = tint)
    ) {
        Column(Modifier.padding(14.dp)) {
            Text(emoji, fontSize = 27.sp)
            Spacer(Modifier.height(8.dp))
            Text(title, color = Navy, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(subtitle, color = Navy, fontSize = 11.sp)
        }
    }
}
