let allShopProducts=[]
document.addEventListener('DOMContentLoaded',()=>{
  const dataSource=AppConfig.getDataSource()
  fetch(dataSource)
    .then(r=>{
      if(!r.ok)throw new Error(r.status)
      return r.json()
    })
    .then(d=>{
      if(d.products&&Array.isArray(d.products)){
        allShopProducts=d.products
        displayProducts(allShopProducts,'shop-products')
      }
      setupAddToCartHandlers()
      // Add readiness marker
      const readyMarker=document.createElement('div')
      readyMarker.id='products-loaded'
      readyMarker.setAttribute('data-ready','true')
      readyMarker.style.display='none'
      document.body.appendChild(readyMarker)
      // Dispatch custom event for Selenium
      document.dispatchEvent(new Event('productsLoaded'))
      console.log('Products loaded event dispatched.')
      const s=document.getElementById('searchInput')
      const o=document.getElementById('sortSelect')
      if(s){
        s.addEventListener('input',()=>{
          filterAndSortProducts()
        })
      }
      if(o){
        o.addEventListener('change',()=>{
          filterAndSortProducts()
        })
      }
    })
    .catch(e=>{
      console.error('Error fetching products.json for Shop page:',e)
    })
})
function filterAndSortProducts(){
  const s=document.getElementById('searchInput')
  const o=document.getElementById('sortSelect')
  let r=[...allShopProducts]
  if(s&&s.value.trim()!==''){
    r=r.filter(p=>p.name.toLowerCase().includes(s.value.toLowerCase()))
  }
  if(o){
    if(o.value==='nameAsc'){
      r.sort((a,b)=>a.name.localeCompare(b.name))
    }else if(o.value==='nameDesc'){
      r.sort((a,b)=>b.name.localeCompare(a.name))
    }else if(o.value==='priceAsc'){
      r.sort((a,b)=>a.price-b.price)
    }else if(o.value==='priceDesc'){
      r.sort((a,b)=>b.price-a.price)
    }
  }
  displayProducts(r,'shop-products')
  setupAddToCartHandlers()
}
