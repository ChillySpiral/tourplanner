package fhtw.at.tourplanner.viewmodel;

import fhtw.at.tourplanner.model.TourModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TourTabViewModelTest {
    private TourTabViewModel tourTabViewModel;

    @BeforeEach
    private void initTestVM(){
        this.tourTabViewModel = new TourTabViewModel();
    }

    @Test
    @DisplayName("Initial Value Test")
    public void TestSetTourModel_V_A(){
        tourTabViewModel.setTourModel(null);
        assertThat(tourTabViewModel.getTitle()).isEqualTo("");
        //ToDo: More Properties
    }

    @Test
    @DisplayName("Set Model Test")
    public void TestSetTourModel_V_B(){
        var expected = new TourModel();
        var expectedTitle = "ExcpectedTitle";
        expected.setTitle(expectedTitle);
        //ToDo: More Properties


        tourTabViewModel.setTourModel(expected);

        assertThat(tourTabViewModel.getTitle()).isEqualTo(expectedTitle);
    }

}