function calculateWeightCategories() {
    const rows = document.querySelectorAll(".activity-list tbody tr");
    const counts = { light: 0, medium: 0, heavy: 0 };

    rows.forEach(row => {
        const weight = row.children[2].textContent.toLowerCase().trim();
        if (counts[weight] !== undefined) {
            counts[weight]++;
        }
    });

    alert(`Light: ${counts.light}\nMedium: ${counts.medium}\nHeavy: ${counts.heavy}`);
}

document.addEventListener("DOMContentLoaded", () => {
    const calculateBtn = document.getElementById("calculate-btn");
    if (calculateBtn) {
        calculateBtn.addEventListener("click", calculateWeightCategories);
    }
});
