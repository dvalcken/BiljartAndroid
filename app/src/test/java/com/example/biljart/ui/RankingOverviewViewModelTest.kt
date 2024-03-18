package com.example.biljart.ui

import com.example.biljart.model.Rank
import org.junit.Test

class RankingOverviewViewModelTest {
    private val viewModel = RankingOverviewViewModel()

    @Test
    fun `test if the viewmodel rankUiState is created`() {
        assert(viewModel.rankUiState != null)
    }

    // test is rankUiState.ranks contains a list of ranks
    @Test
    fun `test if the rankUiState contains a list of Ranks`() {
        assert(viewModel.rankUiState.value.ranks != null)
        // assert that items in the are of type Rank
        assert(viewModel.rankUiState.value.ranks[0] is Rank)
    }
}
