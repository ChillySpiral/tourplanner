package fhtw.at.tourplanner.viewmodel;

import fhtw.at.tourplanner.listener.BindListener;
import fhtw.at.tourplanner.model.TourModel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TourTabViewModelTest {

    @Test
    public void TestBindListener(){
        var testTourTabVM = new TourTabViewModel();
        BindListener bindListener = mock(BindListener.class);
        testTourTabVM.addListener(bindListener);

        testTourTabVM.setTourModel(new TourModel("ExpectedTitle", "ExpectedDescription"));
        verify(bindListener, times(1)).requestReBind();
        verify(bindListener, times(1)).requestUnBind();
    }

    @Test
    public void TestSetTourModel(){
        var testTourTabVM = new TourTabViewModel();
        assertThat(testTourTabVM.data).isNotEqualTo(new TourModel("",""));
        assertThat(testTourTabVM.data.getTitle().getValue()).isEqualTo("");
        assertThat(testTourTabVM.data.getDescription().getValue()).isEqualTo("");

        var expected = new TourModel("ExpectedTitle", "ExpectedDescription");
        testTourTabVM.setTourModel(expected);
        assertThat(testTourTabVM.data).isEqualTo(expected);
    }
}