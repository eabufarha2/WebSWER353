
document.addEventListener('DOMContentLoaded', () => {
  updateCartDisplay();

  const checkoutBtn = document.getElementById('checkoutBtn');
  if (checkoutBtn) {
    checkoutBtn.addEventListener('click', handleCheckout);
  }
});


function updateCartDisplay() {
  const cartSection = document.getElementById('cartItems');
  if (!cartSection) {
    console.log('Not on cart page. Skipping cart display update.');
    return;
  }

  const cartHeader = cartSection.querySelector('.cart-header');
  if (!cartHeader) {
    console.warn('Cart header element not found.');
    return;
  }

  const totalsSection = document.getElementById('cartTotalSection');

  const existingTable = cartSection.querySelector('table');
  if (existingTable) {
    existingTable.remove();
  }

  if (cart.length === 0) {
    cartHeader.innerHTML = '<h2>Your Cart is empty</h2>';
    if (totalsSection) {
      totalsSection.style.display = 'none';
    }
    return;
  }

  if (totalsSection) {
    totalsSection.style.display = 'block';
  }

  cartHeader.innerHTML = '<h2>Your Cart Items</h2>';
  console.log('Displaying cart items.');

  let html = `
    <table>
      <thead>
        <tr>
          <th>Product</th>
          <th>Name</th>
          <th>Price</th>
          <th>Qty</th>
          <th>Remove</th>
        </tr>
      </thead>
      <tbody>
  `;
  const fallbackImg = "https://via.placeholder.com/50?text=No+Image";
  let subtotal = 0;

  cart.forEach((item, index) => {
    const itemTotal = item.qty * item.price;
    subtotal += itemTotal;
    html += `
      <tr data-testid="cart-item-row">
        <td>
          <img src="${item.img}"
               width="50"
               alt="${item.name}"
               data-testid="cart-item-image"
               onerror="this.onerror=null;this.src='${fallbackImg}';" />
        </td>
        <td>
          <a href="product-detail.html?id=${item.id || ''}" data-testid="cart-item-link">
            ${item.name}
          </a>
        </td>
        <td data-testid="cart-item-price">${CURRENCY}${item.price.toFixed(2)}</td>
        <td>
          <button class="qtyBtn" data-index="${index}" data-action="minus" data-testid="qty-minus-btn">-</button>
          <span data-testid="cart-item-qty">${item.qty}</span>
          <button class="qtyBtn" data-index="${index}" data-action="plus" data-testid="qty-plus-btn">+</button>
        </td>
        <td>
          <button class="removeBtn" data-index="${index}" data-testid="cart-remove-btn">X</button>
        </td>
      </tr>
    `;
  });

  html += `</tbody></table>`;
  cartSection.innerHTML += html;

  const total = subtotal + SHIPPING_COST;
  document.getElementById('subtotal').textContent = `${CURRENCY}${subtotal.toFixed(2)}`;
  document.getElementById('total').textContent = `${CURRENCY}${total.toFixed(2)}`;

  // Bind remove / qty events
  const removeBtns = document.querySelectorAll('.removeBtn');
  removeBtns.forEach(btn => btn.addEventListener('click', removeItem));

  const qtyBtns = document.querySelectorAll('.qtyBtn');
  qtyBtns.forEach(btn => btn.addEventListener('click', updateItemQty));

  // Dispatch custom event for Selenium
  document.dispatchEvent(new Event('cartUpdated'));
  console.log('Cart updated event dispatched.');
}

function removeItem(e) {
  const index = parseInt(e.target.getAttribute('data-index'));
  if (isNaN(index) || index < 0 || index >= cart.length) {
    console.warn('Invalid index for removal:', index);
    return;
  }
  const removedItem = cart.splice(index, 1)[0];
  console.log(`Removed item from cart: ${removedItem.name}`);
  saveCartToLocalStorage();
  updateCartCount();
  updateCartDisplay();
}

function updateItemQty(e) {
  const index = parseInt(e.target.getAttribute('data-index'));
  const action = e.target.getAttribute('data-action');

  if (isNaN(index) || index < 0 || index >= cart.length) {
    console.warn('Invalid index for quantity update:', index);
    return;
  }

  if (action === 'minus') {
    cart[index].qty--;
    if (cart[index].qty <= 0) {
      cart.splice(index, 1);
    }
  } else if (action === 'plus') {
    cart[index].qty++;
  } else {
    console.warn('Unknown action for quantity update:', action);
    return;
  }

  saveCartToLocalStorage();
  updateCartCount();
  updateCartDisplay();
}


function handleCheckout() {
  const msgDiv = document.getElementById('checkoutMessage');
  if (!msgDiv) return;

  if (cart.length === 0) {
    msgDiv.style.display = 'block';
    msgDiv.innerHTML = `<p style="color:red;font-size:18px;">
      Your cart is empty. Please add items before checking out.
    </p>`;
    return;
  }

  cart = [];
  saveCartToLocalStorage();
  updateCartCount();
  updateCartDisplay();
  
  document.getElementById('money').style.display = 'none';
  document.getElementById('cartTotalSection').style.display = 'none';

  // 3) Show a confirmation message
  msgDiv.style.display = 'block';
  msgDiv.innerHTML = `
    <h2 style="margin-bottom:10px;">Thank You for Your Purchase!</h2>
    <p>Your order is being processed, and your cart is now empty.</p>
  `;
}

// Dev/Test Functions for Selenium
function resetCart() {
  cart = [];
  saveCartToLocalStorage();
  updateCartCount();
  updateCartDisplay();
  console.log('Cart has been reset.');
}

function seedCart() {
  // Add sample test data to cart
  cart = [
    {
      id: "F1",
      name: "Eternal Bloom Perfume",
      price: 750,  // Already discounted (2500 * 0.3)
      img: "image/mens/invictus-52.jpg",
      qty: 2
    },
    {
      id: "N1",
      name: "Future Essence Perfume",
      price: 750,
      img: "image/bestsellers/jean-paul-60.jpg",
      qty: 1
    },
    {
      id: "P1",
      name: "Eternal Bloom Perfume",
      price: 750,
      img: "image/mens/invictus-52.jpg",
      qty: 3
    }
  ];
  saveCartToLocalStorage();
  updateCartCount();
  updateCartDisplay();
  console.log('Cart has been seeded with test data.');
}

// Bind dev button events
document.addEventListener('DOMContentLoaded', () => {
  const resetBtn = document.getElementById('resetCartBtn');
  const seedBtn = document.getElementById('seedCartBtn');

  if (resetBtn) {
    resetBtn.addEventListener('click', resetCart);
  }

  if (seedBtn) {
    seedBtn.addEventListener('click', seedCart);
  }
});
