Feature: TS002 Search Functionality as Logged In

  Background: Open the Site, Login and Navigate to Search Page
    Given go to "BaseURL"
    When click Log in link
    And type login "username" into email box
    And type login "password" into password box
    And click login button
    Then account avatar should be displayed
    And navigate to "searchPage"
    Then page URL should contain "vimeo.com/search"

  @search
  Scenario: TC001 Validate Search Result Consistency
    When search "jazz"
    Then search results should contain "jazz"
    And close browser

  @search
  Scenario: TC002 Validate Count of Search Results Per Page
    When search "lacrymosa"
    Then results per page should not exceed the max number
    And close browser

  @search
  Scenario: TC003 Validate No Prev Button at First Page
    When search "alan walker faded"
    Then "Prev" button should not be displayed
    And close browser

  @search
  Scenario: TC004 Validate No Next Button at Last Page
    When search "electric blue"
    And go to last result page
    Then "Next" button should not be displayed
    And close browser

  @search
  Scenario: TC005 Validate Prev and Next buttons are Visible at Intermediate Pages
    When search "metallica"
    And go to result page 2
    Then "Prev" button should be displayed
    And "Next" button should be displayed
    And close browser

  @search
  Scenario: TC006 Validate Results Count is Correct
    When search "banderilla"
    Then result count should be correct
    And close browser

  @search
  Scenario: TC007 Validate Results Count for Videos
    When search "rammstein"
    Then result count for "Videos" should be correct
    And close browser

  @search
  Scenario: TC008 Validate Results Count for On Demand
    When search "rammstein"
    And select "ondemand" category
    Then result count for "On Demand" should be correct
    And close browser

  @search
  Scenario: TC009 Validate Results Count for People
    When search "rammstein"
    And select "people" category
    Then result count for "People" should be correct
    And close browser

  @search
  Scenario: TC010 Validate Results Count for Channels
    When search "rammstein"
    And select "channels" category
    Then result count for "Channels" should be correct
    And close browser

  @search
  Scenario: TC011 Validate Results Count for Groups
    When search "rammstein"
    And select "groups" category
    Then result count for "Groups" should be correct
    And close browser

  @search
  Scenario: TC012 Validate Video Links are Working
    When search "animation"
    And click a video link
    Then page title contains video name
    And close browser