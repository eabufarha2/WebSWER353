
document.addEventListener('DOMContentLoaded', () => {
  const params = new URLSearchParams(window.location.search);
  const productId = params.get('id');
  
  if (!productId) {
    console.error('No product ID in the URL.');
    return;
  }

  const dataSource = AppConfig.getDataSource();
  fetch(dataSource)
    .then(res => {
      if (!res.ok) throw new Error('Failed to load products.json');
      return res.json();
    })
    .then(data => {
      const allProducts = [
        ...(data.featured || []),
        ...(data.newArrivals || []),
        ...(data.products || [])
      ];

      // 3) Find the product by ID
      const product = allProducts.find(p => p.id === productId);
      if (!product) {
        console.error('Product not found with id:', productId);
        const detailSection = document.getElementById('product-detail');
        if (detailSection) {
          detailSection.innerHTML = '<h2>Product not found!</h2>';
        }
        return;
      }
      
      // 4) Display product details
      displayProductDetail(product);

      // Add readiness marker
      const readyMarker = document.createElement('div');
      readyMarker.id = 'product-loaded';
      readyMarker.setAttribute('data-ready', 'true');
      readyMarker.style.display = 'none';
      document.body.appendChild(readyMarker);

      // Dispatch custom event for Selenium
      document.dispatchEvent(new Event('productLoaded'));
      console.log('Product loaded event dispatched.');
    })
    .catch(err => {
      console.error('Error fetching product:', err);
    });
});


function displayProductDetail(product) {
  const productImgDiv = document.getElementById('product-image');
  const productInfoDiv = document.getElementById('product-info');

  const discountedPrice = applyDiscount(product.price);
  const formattedOriginalPrice = `${CURRENCY}${product.price.toFixed(2)}`;
  const formattedDiscountedPrice = `${CURRENCY}${discountedPrice.toFixed(2)}`;

  const fallbackImg = "https://via.placeholder.com/400?text=No+Image";

  productImgDiv.innerHTML = `
    <img src="${product.image}" 
         alt="${product.name}" 
         class="main-image"
         onerror="this.onerror=null;this.src='${fallbackImg}';" />
  `;

  productInfoDiv.innerHTML = `
    <h2 data-testid="product-detail-name">${product.name}</h2>
    <h3 data-testid="product-detail-brand">Brand: ${product.brand}</h3>
    <div class="star" data-testid="product-detail-rating">
      ${generateStarsHTML(product.rating)}
    </div>
    <p class="price">
      <span class="original-price" data-testid="product-detail-original-price">${formattedOriginalPrice}</span>
      <span class="discounted-price" data-testid="product-detail-discounted-price">${formattedDiscountedPrice}</span>
    </p>
    <p class="description" data-testid="product-detail-description">
      <strong>Description:</strong>
      Lorem ipsum dolor sit amet, consectetur adipisicing elit.
      (Replace with real product description as needed.)
    </p>

    <!-- A single Add to Cart button for this product -->
    <button class="normal cart"
      data-id="${product.id}"
      data-name="${product.name}"
      data-price="${discountedPrice}"
      data-img="${product.image}"
      data-testid="product-detail-add-to-cart-btn">
      <i class="fa fa-shopping-cart"></i> Add to Cart
    </button>
  `;

  setupAddToCartHandlers();
}
