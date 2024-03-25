@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.biljart.ui

import com.example.biljart.fake.FakeApiRankingRepository
import com.example.biljart.model.Rank
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

class RankingOverviewViewModelTest {

    // See https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-test/
    // for more information this test setup

//    private val viewModel = RankingOverviewViewModel(FakeApiRankingRepository())
    lateinit var viewModel: RankingOverviewViewModel

    @get:Rule
    val testDispatcher = TestDispatchersRule()

    @Before
    fun initializeViewModel() {
        viewModel = RankingOverviewViewModel(FakeApiRankingRepository())
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
        assert(viewModel.rankUiState.value.ranks[0] is Rank)
    }
}

class TestDispatchersRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}
