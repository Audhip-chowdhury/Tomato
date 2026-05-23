export default function LoadingSpinner({ size = 'md' }) {
  const sizeClasses = {
    sm: 'w-6 h-6',
    md: 'w-10 h-10',
    lg: 'w-14 h-14',
  }

  return (
    <div className="flex justify-center items-center py-12">
      <div
        className={`${sizeClasses[size]} border-4 border-gray-200 border-t-tomato rounded-full animate-spin`}
      />
    </div>
  )
}
