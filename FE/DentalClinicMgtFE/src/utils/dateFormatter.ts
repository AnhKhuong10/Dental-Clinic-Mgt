export function formatDate(value: string | null): string {
  if (!value) return "-"

  // parse thủ công yyyy-MM-dd
  if (/^\d{4}-\d{2}-\d{2}$/.test(value)) {
    const [y, m, d] = value.split("-").map(Number)
    return new Date(y, m - 1, d).toLocaleDateString("vi-VN") 
    // Ví dụ: "2000-02-02" -> "02/02/2000"
  }

  // fallback cho ISO 8601 hoặc dạng khác
  const date = new Date(value)
  return isNaN(date.getTime()) ? "-" : date.toLocaleDateString("vi-VN")
}
