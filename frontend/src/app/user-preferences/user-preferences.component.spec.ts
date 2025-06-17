import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { UserPreferencesComponent } from './user-preferences.component';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { provideRouter } from '@angular/router';



describe('UserPreferencesComponent', () => {
  let component: UserPreferencesComponent;
  let fixture: ComponentFixture<UserPreferencesComponent>;
  let httpMock: HttpTestingController;
  let routerSpy: jasmine.SpyObj<Router>;
  let router: Router;

  const mockPreference = {
    id: 1,
    userId: 101,
    goalCategoryId: 2,
    timePerDay: 30,
    frequency: 3
  };

  const mockCategories = [
    { id: 1, name: 'Cat1', description: 'desc1' },
    { id: 2, name: 'Cat2', description: 'desc2' }
  ];


beforeEach(async () => {
  await TestBed.configureTestingModule({
    imports: [UserPreferencesComponent],
    providers: [
      provideRouter([]),
      provideHttpClient(),
      provideHttpClientTesting()
    ]
  }).compileComponents();

  fixture = TestBed.createComponent(UserPreferencesComponent);
  component = fixture.componentInstance;
  httpMock = TestBed.inject(HttpTestingController);

  // Spy on the real router's navigate method
  router = TestBed.inject(Router);
  spyOn(router, 'navigate');
});

  afterEach(() => {
    httpMock.verify();
  });

  it('should create', () => {
    // Arrange & Act done by TestBed setup
    // Assert
    expect(component).toBeTruthy();
  });

  it('should load preference on init', fakeAsync(() => {
    // Arrange & Act
    component.ngOnInit();
    const req = httpMock.expectOne('http://localhost:8080/api/preferences/101');
    expect(req.request.method).toEqual('GET');
    req.flush(mockPreference);

    const catReq = httpMock.expectOne('http://localhost:8080/api/categories');
    expect(catReq.request.method).toBe('GET');
    catReq.flush(mockCategories);

    tick();
    // Assert
    expect(component.preference).toEqual(mockPreference);
    expect(component.newPreference).toEqual(mockPreference);
    expect(component.categories).toEqual(mockCategories);
  }));

  it('should handle error on loadPreference', fakeAsync(() => {
    // Arrange & Act
    component.ngOnInit();
    const req = httpMock.expectOne('http://localhost:8080/api/preferences/101');
    req.error(new ErrorEvent('Network error'));

    const catReq = httpMock.expectOne('http://localhost:8080/api/categories');
    catReq.flush(mockCategories);

    tick();
    // Assert
    expect(component.preference).toBeNull();
    expect(component.newPreference.userId).toBe(101);
    expect(component.newPreference.goalCategoryId).toBeNull();
  }));

  it('should handle error on loadCategories', fakeAsync(() => {
    // Arrange & Act
    component.loadCategories();
    const req = httpMock.expectOne('http://localhost:8080/api/categories');
    req.error(new ErrorEvent('Network error'));
    tick();
    // Assert
    expect(component.categories).toEqual([]);
  }));

  it('getCategoryName should return correct name', () => {
    // Arrange
    component.categories = mockCategories;
    // Act & Assert
    expect(component.getCategoryName(2)).toBe('Cat2');
    expect(component.getCategoryName(999)).toBe('Unknown');
    expect(component.getCategoryName(null)).toBe('');
  });

  it('isFormValid should validate form correctly', () => {
    // Arrange & Act & Assert
    component.newPreference = { userId: 101, goalCategoryId: 1, timePerDay: 10, frequency: 2 };
    expect(component.isFormValid()).toBeTrue();

    component.newPreference = { userId: 101, goalCategoryId: null, timePerDay: 10, frequency: 2 };
    expect(component.isFormValid()).toBeFalse();

    component.newPreference = { userId: 101, goalCategoryId: 1, timePerDay: null, frequency: 2 };
    expect(component.isFormValid()).toBeFalse();

    component.newPreference = { userId: 101, goalCategoryId: 1, timePerDay: 10, frequency: null };
    expect(component.isFormValid()).toBeFalse();
  });

  it('savePreference should not call API if form is invalid', () => {
    // Arrange
    spyOn(component, 'isFormValid').and.returnValue(false);
    // Act
    component.savePreference();
    // Assert
    httpMock.expectNone('http://localhost:8080/api/preferences');
  });

  it('savePreference should call API and navigate on success', fakeAsync(() => {
    // Arrange
    spyOn(component, 'isFormValid').and.returnValue(true);
    component.newPreference = { userId: 101, goalCategoryId: 1, timePerDay: 10, frequency: 2 };
    // Act
    component.savePreference();

    const req = httpMock.expectOne('http://localhost:8080/api/preferences');
    expect(req.request.method).toBe('PUT');
    req.flush({});

    // loadPreference is called after save
    const prefReq = httpMock.expectOne('http://localhost:8080/api/preferences/101');
    prefReq.flush(mockPreference);

    tick();
    // Assert
    expect(router.navigate).toHaveBeenCalledWith(['/exerciseschedule']);
  }));

  it('savePreference should handle error', fakeAsync(() => {
    // Arrange
    spyOn(component, 'isFormValid').and.returnValue(true);
    component.newPreference = { userId: 101, goalCategoryId: 1, timePerDay: 10, frequency: 2 };
    // Act
    component.savePreference();

    const req = httpMock.expectOne('http://localhost:8080/api/preferences');
    req.error(new ErrorEvent('Network error'));
    tick();
    // Assert
    expect(router.navigate).not.toHaveBeenCalled();
  }));

  it('resetNewPreference should reset fields', () => {
    // Arrange
    component.newPreference = { userId: 101, goalCategoryId: 5, timePerDay: 20, frequency: 3 };
    // Act
    (component as any).resetNewPreference();
    // Assert
    expect(component.newPreference).toEqual({
      userId: 101,
      goalCategoryId: null,
      timePerDay: null,
      frequency: null
    });
  });
});