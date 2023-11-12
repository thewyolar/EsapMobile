package javavlsu.kb.esap.esapmobile.data

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javavlsu.kb.esap.esapmobile.domain.api.ApiResponse
import javavlsu.kb.esap.esapmobile.domain.model.request.AppointmentRequest
import javavlsu.kb.esap.esapmobile.domain.model.response.AppointmentResponse
import javavlsu.kb.esap.esapmobile.domain.model.response.DoctorResponse
import javavlsu.kb.esap.esapmobile.domain.model.response.PatientResponse
import javavlsu.kb.esap.esapmobile.domain.repository.MainRepository
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
): BaseViewModel() {

    private val _doctorResponse = MutableLiveData<ApiResponse<DoctorResponse>>()
    val doctorResponse = _doctorResponse

    private val _patientResponse = MutableLiveData<ApiResponse<PatientResponse>>()
    val patientResponse = _patientResponse

    private val _doctorListResponse = MutableLiveData<ApiResponse<List<DoctorResponse>>>()
    val doctorListResponse = _doctorListResponse

    private val _doctorResponseById = MutableLiveData<ApiResponse<DoctorResponse>>()
    val doctorResponseById = _doctorResponseById

    private val _makeAppointmentResponse = MutableLiveData<ApiResponse<String>>()
    val makeAppointmentResponse = _makeAppointmentResponse

    private val _patientAppointmentList = MutableLiveData<ApiResponse<List<AppointmentResponse>>>()
    val patientAppointmentList = _patientAppointmentList

    fun getDoctor(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _doctorResponse,
        coroutinesErrorHandler
    ) {
        mainRepository.getDoctor()
    }

    fun getPatient(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _patientResponse,
        coroutinesErrorHandler
    ) {
        mainRepository.getPatient()
    }

    fun getDoctorList(date: LocalDate, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _doctorListResponse,
        coroutinesErrorHandler
    ) {
        mainRepository.getDoctorList(date)
    }

    fun getDoctorById(doctorId: Long, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _doctorResponseById,
        coroutinesErrorHandler
    ) {
        mainRepository.getDoctorById(doctorId)
    }

    fun makeAppointment(scheduleId: Long,
                        appointmentRequest: AppointmentRequest,
                        coroutinesErrorHandler: CoroutinesErrorHandler
    ) = baseRequest(
        _makeAppointmentResponse,
        coroutinesErrorHandler
    ) {
        mainRepository.makeAppointment(scheduleId, appointmentRequest)
    }

    fun getPatientAppointments(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _patientAppointmentList,
        coroutinesErrorHandler
    ) {
        mainRepository.getPatientAppointments()
    }
}