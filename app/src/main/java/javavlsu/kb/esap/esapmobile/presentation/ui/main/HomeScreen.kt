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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MedicalServices
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import javavlsu.kb.esap.esapmobile.R
import javavlsu.kb.esap.esapmobile.data.CoroutinesErrorHandler
import javavlsu.kb.esap.esapmobile.data.MainViewModel
import javavlsu.kb.esap.esapmobile.data.TokenViewModel
import javavlsu.kb.esap.esapmobile.domain.api.ApiResponse
import javavlsu.kb.esap.esapmobile.presentation.component.Button
import javavlsu.kb.esap.esapmobile.presentation.theme.Gray40
import javavlsu.kb.esap.esapmobile.presentation.theme.Green80

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    tokenViewModel: TokenViewModel = hiltViewModel(),
    navigateToSignIn: () -> Unit,
) {
    var responseMessage by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val token by tokenViewModel.token.observeAsState()
    val userInfoResponse by mainViewModel.userInfoResponse.observeAsState()

    LaunchedEffect(token) {
        val tokenValue = token.toString()
        mainViewModel.getUserInfo("Bearer $tokenValue",
            object : CoroutinesErrorHandler {
                override fun onError(message: String) {
                    responseMessage = message
                    showDialog = true
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (userInfoResponse is ApiResponse.Loading) {
            CircularProgressIndicator(
                color = Color.Blue,
                modifier = Modifier.padding(16.dp)
            )
        } else if (userInfoResponse is ApiResponse.Success) {
            val user = (userInfoResponse as ApiResponse.Success).data

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${stringResource(R.string.hello)} ${user.firstName} ${stringResource(R.string.smile)}",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.W600,
                    color = Color.Black,
                    textAlign = TextAlign.Left
                )

                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(color = Gray40, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        tint = Color.Gray,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center)
                    )
                }
            }
            Spacer(modifier = Modifier.size(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Outlined.MedicalServices,
                    tint = Color.Blue,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = stringResource(R.string.medical_record),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Blue,
                    textAlign = TextAlign.Left
                )
            }
            Spacer(modifier = Modifier.size(32.dp))

            Button(
                text = stringResource(R.string.make_appointment),
                color = Green80,
                onClick = {}
            )
            Spacer(modifier = Modifier.size(16.dp))

            Button(
                text = stringResource(R.string.logout),
                color = Color.Red,
                onClick = {
                    tokenViewModel.deleteToken()
                    navigateToSignIn()
                }
            )
        }
    }
}