/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        tomato: '#E23744',
        'tomato-dark': '#C42D3A',
        'text-dark': '#3D4152',
        'text-muted': '#93959F',
        'bg-light': '#F8F8F8',
      },
      fontFamily: {
        sans: ['Inter', 'system-ui', 'sans-serif'],
      },
    },
  },
  plugins: [],
}
