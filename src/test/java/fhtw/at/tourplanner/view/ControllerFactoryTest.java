package fhtw.at.tourplanner.view;

import fhtw.at.tourplanner.viewmodel.HomeViewModel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ControllerFactoryTest {

    @Test
    public void TestCreate(){
        assertThat(ControllerFactory.getInstance().create(HomeController.class)).isInstanceOf(HomeController.class);
        assertThat(ControllerFactory.getInstance().create(LogTableController.class)).isInstanceOf(LogTableController.class);
        assertThat(ControllerFactory.getInstance().create(SearchBarController.class)).isInstanceOf(SearchBarController.class);
        assertThat(ControllerFactory.getInstance().create(TourListController.class)).isInstanceOf(TourListController.class);
        assertThat(ControllerFactory.getInstance().create(TourTabController.class)).isInstanceOf(TourTabController.class);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> ControllerFactory.getInstance().create(HomeViewModel.class));
    }


}