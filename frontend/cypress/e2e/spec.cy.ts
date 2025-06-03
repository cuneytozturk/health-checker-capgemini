describe('User Preferences Page E2E', () => {
    const category: string = 'Neck - Pijn in de nek door slechte houding of langdurig schermgebruik.';
    const categoryId: number = 1;

  beforeEach(() => {
    // Visit the user preferences page before each test
    cy.visit('/userpreferences');
    
  });

  it('should display the user preferences form', () => {
    // Assert form and fields are visible
    cy.contains('User Preferences Survey').should('be.visible');
    cy.get('form.add-form').should('be.visible');
    cy.get('select[name="goalCategoryId"]').should('be.visible');
    cy.get('input[name="timePerDay"]').should('be.visible');
    cy.get('input[name="frequency"]').should('be.visible');
    cy.get('button[type="submit"]').should('be.visible');
  });

  it('should require all fields before enabling submit', () => {
    // All fields empty, button should be disabled
    cy.get('input[name="timePerDay"]').clear();
    cy.get('input[name="frequency"]').clear()
    cy.get('button[type="submit"]').should('be.disabled');

    // Fill only one field
    cy.get('select[name="goalCategoryId"]').select(category);
    cy.get('button[type="submit"]').should('be.disabled');

    // Fill second field
    cy.get('input[name="timePerDay"]').clear().type('20');
    cy.get('button[type="submit"]').should('be.disabled');

    // Fill third field
    cy.get('input[name="frequency"]').clear().type('3');
    cy.get('button[type="submit"]').should('not.be.disabled');
  });

  it('should submit preferences and navigate to exercise schedule', () => {
    // Fill out the form
    cy.get('select[name="goalCategoryId"]').select(1);
    cy.get('input[name="timePerDay"]').clear().type('20');
    cy.get('input[name="frequency"]').clear().type('2');

    // Submit the form
    cy.get('button[type="submit"]').click();

    // Assert navigation (adjust path if needed)
    cy.url().should('include', '/exerciseschedule');
  });

  it('should persist and display saved preferences on reload', () => {


    // Fill out and submit the form
    cy.get('select[name="goalCategoryId"]').select(category);
    cy.get('input[name="timePerDay"]').clear().type('30');
    cy.get('input[name="frequency"]').clear().type('4');
    cy.get('button[type="submit"]').click();

    // Should navigate
    cy.url().should('include', '/exerciseschedule');

    // Go back to preferences page
    cy.visit('/userpreferences');

    // Assert that the form is pre-filled with saved values
    cy.get('select[name="goalCategoryId"]').should('have.value', categoryId);
    cy.get('input[name="timePerDay"]').should('have.value', '30');
    cy.get('input[name="frequency"]').should('have.value', '4');
  });
});