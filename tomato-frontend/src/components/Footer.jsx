export default function Footer() {
  return (
    <footer className="bg-text-dark text-white mt-auto">
      <div className="max-w-7xl mx-auto px-4 py-8">
        <div className="flex flex-col md:flex-row justify-between items-center gap-4">
          <div>
            <h3 className="text-xl font-bold text-tomato">Tomato</h3>
            <p className="text-gray-400 text-sm mt-1">Order food you love, delivered fast.</p>
          </div>
          <div className="flex gap-6 text-sm text-gray-400">
            <a href="/" className="hover:text-white transition-colors">Home</a>
            <a href="/restaurants" className="hover:text-white transition-colors">Restaurants</a>
            <a href="/cart" className="hover:text-white transition-colors">Cart</a>
          </div>
          <p className="text-gray-500 text-sm">&copy; 2026 Tomato. All rights reserved.</p>
        </div>
      </div>
    </footer>
  )
}
