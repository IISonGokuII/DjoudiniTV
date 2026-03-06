package com.nextgen.iptv.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nextgen.iptv.data.local.entity.EpisodeEntity
import com.nextgen.iptv.data.local.entity.SeasonEntity
import com.nextgen.iptv.data.local.entity.SeriesEntity
import com.nextgen.iptv.domain.repository.SeriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SeriesDetailUiState(
    val isLoading: Boolean = true,
    val series: SeriesEntity? = null,
    val seasons: List<SeasonEntity> = emptyList(),
    val episodes: Map<Int, List<EpisodeEntity>> = emptyMap(),
    val selectedSeason: Int = 1,
    val error: String? = null
)

@HiltViewModel
class SeriesDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val seriesRepository: SeriesRepository
) : ViewModel() {

    private val seriesId: String = checkNotNull(savedStateHandle["seriesId"])

    private val _uiState = MutableStateFlow(SeriesDetailUiState())
    val uiState: StateFlow<SeriesDetailUiState> = _uiState.asStateFlow()

    init {
        loadSeriesDetail()
    }

    private fun loadSeriesDetail() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val series = seriesRepository.getSeriesById(seriesId)
                val seasons = seriesRepository.getSeasonsBySeries(seriesId).first()
                
                val episodesMap = mutableMapOf<Int, List<EpisodeEntity>>()
                seasons.forEach { season ->
                    val episodes = seriesRepository.getEpisodesBySeason(season.id).first()
                    episodesMap[season.seasonNumber] = episodes
                }
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    series = series,
                    seasons = seasons,
                    episodes = episodesMap,
                    selectedSeason = seasons.firstOrNull()?.seasonNumber ?: 1
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    fun selectSeason(seasonNumber: Int) {
        _uiState.value = _uiState.value.copy(selectedSeason = seasonNumber)
    }

    fun refresh() {
        loadSeriesDetail()
    }
}
