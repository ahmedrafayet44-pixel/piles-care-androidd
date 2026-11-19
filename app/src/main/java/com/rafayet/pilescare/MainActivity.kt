package com.rafayet.pilescare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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

private val Background = Color(0xFF101820)
private val CardColor = Color(0xFF1B2935)
private val Accent = Color(0xFF36C9A0)
private val TextWhite = Color(0xFFF4F7FA)
private val Muted = Color(0xFFAAB8C2)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PilesCareApp()
        }
    }
}

@Composable
fun PilesCareApp() {
    var water by remember { mutableIntStateOf(0) }
    var stool by remember { mutableStateOf("Soft") }
    var burning by remember { mutableIntStateOf(0) }
    var selectedTab by remember { mutableIntStateOf(0) }

    MaterialTheme {
        Scaffold(
            containerColor = Background,
            bottomBar = {
                NavigationBar(
                    containerColor = CardColor,
                    contentColor = Accent
                ) {
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        icon = { Text("⌂", fontSize = 22.sp) },
                        label = { Text("Home") }
                    )
                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        icon = { Text("✓", fontSize = 22.sp) },
                        label = { Text("Track") }
                    )
                    NavigationBarItem(
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                        icon = { Text("♡", fontSize = 22.sp) },
                        label = { Text("Guide") }
                    )
                }
            }
        ) { padding ->

            if (selectedTab == 0) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    ModernHomeScreen(
                        water = water,
                        onAddWater = {
                            water = (water + 250).coerceAtMost(5000)
                        },
                        stool = stool,
                        onSelectStool = { stool = it },
                        burning = burning,
                        onBurningChange = { burning = it }
                    )
                }

            } else {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    if (selectedTab == 1) {
                        Text(
                            "Daily Tracking / দৈনিক ট্র্যাকিং",
                            color = TextWhite,
                            fontSize = 23.sp,
                            fontWeight = FontWeight.Bold
                        )

                        WaterCard(water) {
                            water = (water + 250).coerceAtMost(5000)
                        }

                        StoolCard(stool) { stool = it }
                        SymptomCard(burning) { burning = it }

                    } else {
                        Text(
                            "Care Guide / যত্নের পরামর্শ",
                            color = TextWhite,
                            fontSize = 23.sp,
                            fontWeight = FontWeight.Bold
                        )

                        InfoCard(
                            "পানি পান করুন",
                            "Drink enough water throughout the day."
                        )

                        InfoCard(
                            "আঁশযুক্ত খাবার খান",
                            "Try vegetables, fruits and other fiber-rich foods."
                        )

                        InfoCard(
                            "চাপ দিয়ে পায়খানা করবেন না",
                            "Avoid straining and sitting too long on the toilet."
                        )

                        InfoCard(
                            "কুসুম গরম পানিতে বসুন",
                            "A warm sitz bath may help relieve discomfort."
                        )

                        Disclaimer()
                    }
                }
            }
        }
    }
}


@Composable
fun HomeHeader() {
    Column {
        Text(
            "Piles Care",
            color = Accent,
            fontSize = 29.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            "আপনার প্রতিদিনের যত্নের সঙ্গী",
            color = TextWhite,
            fontSize = 16.sp
        )
        Text(
            "Your daily health companion",
            color = Muted,
            fontSize = 13.sp
        )
    }
}

@Composable
fun WaterCard(water: Int, onAdd: () -> Unit) {
    CareCard {
        Text("💧 Water Tracking / পানি ট্র্যাকিং", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        Text("$water ml", color = Accent, fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Text("Daily goal: 3000 ml / দৈনিক লক্ষ্য", color = Muted)
        Spacer(Modifier.height(10.dp))
        LinearProgressIndicator(
            progress = { (water / 3000f).coerceIn(0f, 1f) },
            modifier = Modifier.fillMaxWidth(),
            color = Accent,
            trackColor = Background
        )
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = onAdd,
            colors = ButtonDefaults.buttonColors(containerColor = Accent)
        ) {
            Text("+ Add 250 ml", color = Background)
        }
    }
}

@Composable
fun FoodCard() {
    CareCard {
        Text("🥗 Food Tracking / খাবার ট্র্যাকিং", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("আজ কী খেয়েছেন?", color = Muted)
        Text("What did you eat today?", color = Muted, fontSize = 13.sp)
        Spacer(Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FoodChip("🥬 সবজি")
            FoodChip("🍎 ফল")
            FoodChip("🌾 আঁশ")
        }
        Text(
            "খাবারের তথ্য সংরক্ষণের সুবিধা পরের ধাপে যোগ হবে।",
            color = Muted,
            fontSize = 12.sp
        )
    }
}

@Composable
fun FoodChip(label: String) {
    Surface(
        color = Background,
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(label, color = TextWhite, modifier = Modifier.padding(10.dp), fontSize = 12.sp)
    }
}

@Composable
fun StoolCard(stool: String, onSelect: (String) -> Unit) {
    CareCard {
        Text("🚽 Stool Tracking / পায়খানার অবস্থা", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))
        Text("আজ পায়খানা কেমন ছিল?", color = Muted)
        Spacer(Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Soft", "Normal", "Hard").forEach { option ->
                FilterChip(
                    selected = stool == option,
                    onClick = { onSelect(option) },
                    label = { Text(option) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Accent,
                        selectedLabelColor = Background
                    )
                )
            }
        }
        Text("নির্বাচন: $stool", color = Accent)
    }
}

@Composable
fun SymptomCard(burning: Int, onChange: (Int) -> Unit) {
    CareCard {
        Text("❤️ Symptom Tracking / উপসর্গ ট্র্যাকিং", color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("জ্বালাপোড়া কতটা?", color = Muted)
        Text("Burning level: $burning / 10", color = Accent, fontSize = 17.sp)
        Slider(
            value = burning.toFloat(),
            onValueChange = { onChange(it.toInt()) },
            valueRange = 0f..10f,
            steps = 9
        )
        Text("0 = নেই / None     10 = খুব বেশি / Severe", color = Muted, fontSize = 12.sp)
    }
}

@Composable
fun InfoCard(title: String, description: String) {
    CareCard {
        Text(title, color = Accent, fontSize = 17.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(5.dp))
        Text(description, color = TextWhite, fontSize = 14.sp)
    }
}

@Composable
fun Disclaimer() {
    Text(
        "নোট: এই অ্যাপটি স্বাস্থ্য তথ্য ট্র্যাক করার জন্য। এটি রোগ নির্ণয় বা চিকিৎসকের বিকল্প নয়।",
        color = Muted,
        fontSize = 12.sp
    )
}

@Composable
fun CareCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = CardColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }
}
