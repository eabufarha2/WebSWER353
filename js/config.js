/**
 * Configuration file for test data and environment settings
 * This allows Selenium tests to use alternative data sources
 */

// Configuration object
const AppConfig = {
  // Default data source
  defaultDataSource: 'products.json',

  /**
   * Get the products data source file
   * Can be overridden via:
   * 1. URL parameter: ?dataSource=test-products.json
   * 2. Environment variable (set in localStorage): localStorage.setItem('DATA_SOURCE', 'test-products.json')
   *
   * @returns {string} Path to the products JSON file
   */
  getDataSource: function() {
    // Check URL parameters first
    const urlParams = new URLSearchParams(window.location.search);
    const urlDataSource = urlParams.get('dataSource');
    if (urlDataSource) {
      console.log(`Using data source from URL parameter: ${urlDataSource}`);
      return urlDataSource;
    }

    // Check localStorage (simulating environment variable)
    const envDataSource = localStorage.getItem('DATA_SOURCE');
    if (envDataSource) {
      console.log(`Using data source from environment: ${envDataSource}`);
      return envDataSource;
    }

    // Return default
    console.log(`Using default data source: ${this.defaultDataSource}`);
    return this.defaultDataSource;
  },

  /**
   * Set the data source in localStorage (for test environment simulation)
   * @param {string} dataSource - Path to the data source file
   */
  setDataSource: function(dataSource) {
    localStorage.setItem('DATA_SOURCE', dataSource);
    console.log(`Data source set to: ${dataSource}`);
  },

  /**
   * Reset to default data source
   */
  resetDataSource: function() {
    localStorage.removeItem('DATA_SOURCE');
    console.log('Data source reset to default.');
  }
};

// Make it globally available
window.AppConfig = AppConfig;
