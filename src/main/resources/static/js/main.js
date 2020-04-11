document.addEventListener("DOMContentLoaded", function () {
    const utils = window.ffbUtils;
    utils.init();
    utils.spinner.createSpinner();
    utils.spinner.hideSpinner();
    utils.ajax.getProductionLocations();
    utils.ajax.getCubes();
});
