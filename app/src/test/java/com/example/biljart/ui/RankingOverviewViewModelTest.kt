@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package com.example.biljart.ui

import com.example.biljart.fake.FakeApiPlayerRepository
import com.example.biljart.model.Player
import com.example.biljart.ui.rankingcomponents.RankingOverviewViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class RankingOverviewViewModelTest { // Lesson 8  47' until end

    // See https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-test/
    // for more information on this test setup

    lateinit var viewModel: RankingOverviewViewModel

    @get:Rule
    val testDispatcher = TestDispatchersRule()

    @Before
    fun initializeViewModel() {
        viewModel = RankingOverviewViewModel(FakeApiPlayerRepository())
    }

    @Test
    fun `test if the viewmodel rankUiState is created`() {
        assert(viewModel.rankUiState != null)
    }

    // test is rankUiState.ranks contains a list of ranks
    @Test
    fun `test if the rankUiState contains a list of Ranks`() {
        assert(viewModel.rankUiState.value.ranks != null)
        // assert that items in the are of type Rank
        assert(viewModel.rankUiState.value.ranks[0] is Player)
    }
}

class TestDispatchersRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}
