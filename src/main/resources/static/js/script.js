console.log("Script is loaded.");

// Get the current theme from localStorage or default to "light"
let currentTheme = getTheme();

// Set the initial theme on page load
document.querySelector("html").classList.add(currentTheme);

// Add event listener to the theme change button
document.getElementById("theme_change_button").addEventListener("click", (event) => {
    const oldTheme = currentTheme;

    // Toggle the theme
    if (currentTheme === "dark") {
        currentTheme = "light";
    } else {
        currentTheme = "dark";
    }

    // Update the theme in localStorage
    setTheme(currentTheme);

    // Update the HTML element classes
    document.querySelector("html").classList.remove(oldTheme);
    document.querySelector("html").classList.add(currentTheme);
});

// Function to set the theme in localStorage
function setTheme(theme) {
    localStorage.setItem("theme", theme);
}

// Function to get the theme from localStorage or default to "light"
function getTheme() {
    let theme = localStorage.getItem("theme");
    return theme ? theme : "light";
}
