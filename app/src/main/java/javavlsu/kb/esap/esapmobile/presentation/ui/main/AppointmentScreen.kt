package javavlsu.kb.esap.esapmobile.presentation.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import javavlsu.kb.esap.esapmobile.data.CalendarViewModel
import javavlsu.kb.esap.esapmobile.data.CoroutinesErrorHandler
import javavlsu.kb.esap.esapmobile.data.MainViewModel
import javavlsu.kb.esap.esapmobile.domain.api.ApiResponse
import javavlsu.kb.esap.esapmobile.domain.model.response.DoctorResponse
import javavlsu.kb.esap.esapmobile.presentation.component.Calendar
import javavlsu.kb.esap.esapmobile.presentation.component.CircularProgress
import javavlsu.kb.esap.esapmobile.presentation.component.VerticalGrid
import javavlsu.kb.esap.esapmobile.presentation.data.TimeSlot
import javavlsu.kb.esap.esapmobile.presentation.data.calculateAvailableTimeSlots
import javavlsu.kb.esap.esapmobile.presentation.theme.Gray20
import javavlsu.kb.esap.esapmobile.presentation.theme.Gray40
import javavlsu.kb.esap.esapmobile.presentation.theme.NightBlue
import java.time.format.DateTimeFormatter

@Composable
fun AppointmentScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    calendarViewModel: CalendarViewModel = hiltViewModel()
) {
    var responseMessage by remember { mutableStateOf("") }
    val doctorListResponse by mainViewModel.doctorListResponse.observeAsState()
    val data by calendarViewModel.calendarData.observeAsState()

    LaunchedEffect(data!!.selectedDate) {
        mainViewModel.getDoctorList(
            data!!.selectedDate.date,
            object : CoroutinesErrorHandler {
                override fun onError(message: String) {
                    responseMessage = message
                }
            }
        )
    }

    if (doctorListResponse is ApiResponse.Loading) {
        CircularProgress()
    } else if (doctorListResponse is ApiResponse.Success) {
        val doctors = (doctorListResponse as ApiResponse.Success).data
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Calendar()
            Spacer(modifier = Modifier.size(30.dp))

            Row(modifier = Modifier
                .padding(8.dp)
                .align(Alignment.Start),
            ) {
                Text(
                    text = "ВРАЧИ",
                    fontSize = 20.sp,
                    color = NightBlue,
                    fontWeight = FontWeight.W600,
                )
            }
            if (doctors.isNotEmpty()) {
                LazyColumn {
                    items(doctors) { doctor ->
                        DoctorCard(doctor)
                    }
                }
            } else {
                Text("Нет данных о врачах.")
            }
        }
    }
}

@Composable
fun DoctorCard(doctor: DoctorResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Gray40)
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        tint = Color.Gray,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "${doctor.lastName} ${doctor.firstName} ${doctor.patronymic}",
                        fontWeight = FontWeight.W500,
                        fontSize = 20.sp
                    )
                    Text(
                        text = doctor.specialization,
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (doctor.schedules.isNotEmpty()) {
                Text(
                    text = "Доступное время приема:",
                    fontWeight = FontWeight.W500,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                val availableTimeSlots = calculateAvailableTimeSlots(doctor.schedules, doctor.schedules[0].appointments)
//                LazyVerticalGrid(
//                    columns = GridCells.Fixed(4),
//                    content = {
//                        items(availableTimeSlots) { timeSlot ->
//                            TimeSlotCard(timeSlot)
//                        }
//                    },
//                    horizontalArrangement = Arrangement.spacedBy(8.dp),
//                    verticalArrangement = Arrangement.spacedBy(8.dp)
//                )
                VerticalGrid(
                    columns = 4,
                    content = {
                        availableTimeSlots.forEach { timeSlot ->
                            TimeSlotCard(timeSlot)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun TimeSlotCard(
    timeSlot: TimeSlot,
) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(containerColor = Gray20)
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = 18.dp,
                vertical = 8.dp
            ),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = timeSlot.startTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                color = Color.Gray,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}