(function () {
    if (window.ffbUtils !== undefined) {
        return;
    }

    const ffbUtils = {};

    ffbUtils.templates = {};
    ffbUtils.init = function () {
        ffbUtils.templates.record = document.querySelector('#template-record').content;
        const createRecordView = document.body.querySelector('.ffb-edit-record-wrapper');
        ffbUtils.store.createRecordView = createRecordView;
        createRecordView.style.display = 'none';

        const dropDown = createRecordView.querySelector('.ffb-locations-dropdown-wrapper');
        dropDown.addEventListener('click', (event) => {
            //TODO: move to function
            const locationField = document.body.querySelector('.ffb-edit-record-location-name');
            locationField.innerText = event.target.innerText;
            dropDown.classList.add('display-none');
            event.preventDefault();
            event.stopImmediatePropagation();
        });

        document.body.addEventListener('click', () => {
            dropDown.classList.add('display-none');
        });
        dropDown.classList.add('display-none');
        createRecordView.querySelector('.ffb-edit-field-location')
            .addEventListener('click', function (event) {
                //TODO: move to function
                const dropDown = document.body.querySelector('.ffb-locations-dropdown-wrapper');
                dropDown.classList.remove('display-none');
                const locations = document.body.querySelector('.ffb-locations-dropdown');
                locations.innerHTML = '';
                ffbUtils.store.locations.forEach((l) => {
                    locations.append(ffbUtils.location.createDropDownElement(l.name));
                });
                event.preventDefault();
                event.stopImmediatePropagation();
            });


        createRecordView.querySelector('.ffb-edit-record-cancel-btn')
            .addEventListener('click', () => {
                ffbUtils.spinner.fullScreenOverlay.style.display = 'none';
                ffbUtils.store.createRecordView.style.display = 'none';
            });

        createRecordView.querySelector('.ffb-edit-record-save-btn')
            .addEventListener('click', function () {
                ffbUtils.spinner.fullScreenOverlay.style.display = 'none';
                ffbUtils.store.createRecordView.style.display = 'none';
                const color = document.body.querySelector('.ffb-edit-record-color').value;
                const size = document.body.querySelector('.ffb-edit-record-size').value;
                const locationField = document.body.querySelector('.ffb-edit-record-location-name');
                const prodLocation = ffbUtils.store.locations.find(
                    (l) => l.name === locationField.innerText
                );
                let cube = {
                    color: color,
                    productionLocation: prodLocation,
                    size: size
                };
                if (ffbUtils.store.editCube) {
                    const editCube = ffbUtils.store.editCube;
                    cube.id = editCube.id;
                    cube.size = editCube.size;
                    ffbUtils.ajax
                        .updateCube(cube)
                        .then(
                            (data) => {
                                ffbUtils.cube.refreshTable();
                            },
                            (error) => console.error(error)
                        );
                } else {
                    ffbUtils.ajax
                        .saveCube(cube)
                        .then(
                            (data) => {
                                ffbUtils.cube.refreshTable();
                            },
                            (error) => console.error(error)
                        );
                }
            });

        document.body.querySelector('.ffb-create-record')
            .addEventListener('click', () => {
                //TODO: rename method
                ffbUtils.cube.createNew(false);
            });

        document.body.querySelector('.ffb-refresh-records')
            .addEventListener('click', () => {
                ffbUtils.cube.refreshTable();
            });

        ffbUtils.store.cubeTable = document.querySelector('.ffb-table-content');
    };

    ffbUtils.store = {
        locations: [],
        cubes: [],
        cubeViews: [],
        editCube: null
    };

    ffbUtils.spinner = {};
    ffbUtils.spinner.spinnerContainerClassName = 'ffb-spinner-container';
    ffbUtils.spinner.spinnerClassName = 'ffb-spinner';

    ffbUtils.spinner.createSpinner = function () {
        if (document.body.querySelector(`.${this.spinnerContainerClassName}`)) {
            return;
        }

        this.fullScreenOverlay = document.querySelector('.full-screen-overlay');
        this.fullScreenOverlay.style.display = 'none';
        const spinnerContainer = document.createElement('div');
        spinnerContainer.classList.add(this.spinnerContainerClassName);

        const spinner = document.createElement('div');
        spinner.classList.add(this.spinnerClassName);
        spinnerContainer.append(spinner);

        spinner.innerHTML = ` <div class="sk-grid">
                                   <div class="sk-grid-cube"></div>
                                   <div class="sk-grid-cube"></div>
                                   <div class="sk-grid-cube"></div>
                                   <div class="sk-grid-cube"></div>
                                   <div class="sk-grid-cube"></div>
                                   <div class="sk-grid-cube"></div>
                                   <div class="sk-grid-cube"></div>
                                   <div class="sk-grid-cube"></div>
                                   <div class="sk-grid-cube"></div>
                               </div>
                             `;
        document.body.prepend(spinnerContainer);
        ffbUtils.spinner.spinnerContainer = spinnerContainer;
    };
    ffbUtils.spinner.showSpinner = function () {
        this.fullScreenOverlay.style.display = '';
        this.spinnerContainer.style.display = '';
    };
    ffbUtils.spinner.hideSpinner = function () {
        this.fullScreenOverlay.style.display = 'none';
        this.spinnerContainer.style.display = 'none';
    };


    ffbUtils.ajax = {
        baseUrl: '',
        get productionLocationsUrl() {
            return this.baseUrl + '/production-locations';
        },
        get cubesUrl() {
            return this.baseUrl + '/cubes';
        },
    };
    ffbUtils.ajax.getProductionLocations = function () {
        ffbUtils.spinner.showSpinner();
        return window.axios
            .get(this.productionLocationsUrl)
            .then((response) => {
                ffbUtils.store.locations = response.data;
                ffbUtils.spinner.hideSpinner();
            })
            .catch((err) => {
                console.error(err);
                ffbUtils.spinner.hideSpinner();
            });
    };

    ffbUtils.ajax.getCubes = function () {
        ffbUtils.spinner.showSpinner();
        return window.axios
            .get(this.cubesUrl)
            .then((response) => {
                ffbUtils.store.cubes = response.data;
                ffbUtils.spinner.hideSpinner();
            })
            .catch((err) => {
                console.error(err);
                ffbUtils.spinner.hideSpinner();
            });
    };

    //return promise
    ffbUtils.ajax.getCubeById = function (id) {
        return window.axios.get(`${this.cubesUrl}/${id}`)
    };

    //return promise
    ffbUtils.ajax.deleteCubeById = function (id) {
        return window.axios.delete(`${this.cubesUrl}/${id}`);
    };

    //return promise
    ffbUtils.ajax.saveCube = function (cube) {
        return window.axios.post(`${this.cubesUrl}`, cube);
    };

    //return promise
    ffbUtils.ajax.updateCube = function (cube) {
        return window.axios.put(`${this.cubesUrl}/${cube.id}`, cube);
    };

    ffbUtils.cube = {};
    ffbUtils.cube.refreshTable = function () {
        ffbUtils.ajax.getCubes().then((data) => {
            ffbUtils.cube.cleanTable();
            ffbUtils.cube.fillTable();
        })
    };

    ffbUtils.cube.edit = function (cube) {
        ffbUtils.cube.createNew(true, cube);
    };
    ffbUtils.cube.delete = function (cube) {
        ffbUtils.spinner.showSpinner();
        ffbUtils.ajax.deleteCubeById(cube.id)
            .then((response) => {
                //TODO: show modal with ok
                ffbUtils.spinner.hideSpinner();
                ffbUtils.cube.refreshTable();
            })
            .catch((err) => {
                console.error(err);
                ffbUtils.spinner.hideSpinner();
            });
    };

    ffbUtils.cube.createRecord = function (cube) {
        const viewTemplate = ffbUtils.templates.record;
        const colorCell = viewTemplate.querySelector('.cube-color');
        colorCell.innerHTML = `
            <div class="ffb-cube-color-sample" style="background-color: ${cube.color}"></div>
            ${cube.color}
            `;
        viewTemplate.querySelector('.cube-production-location').innerText = cube.productionLocation.name;
        viewTemplate.querySelector('.cube-size').innerText = cube.size;
        let view = document.importNode(viewTemplate, true);
        view.cubeId = cube.id;
        view.querySelector('.fbb-cube-edit-btn')
            .addEventListener('click', function (event) {
                ffbUtils.cube.edit(cube)
            });
        view.querySelector('.fbb-cube-delete-btn')
            .addEventListener('click', function () {
                ffbUtils.cube.delete(cube);
            });

        ffbUtils.store.cubeViews.push(view);

        return view;
    };

    ffbUtils.cube.createNew = function (edit, cube) {
        ffbUtils.store.createRecordView.style.display = '';
        ffbUtils.spinner.fullScreenOverlay.style.display = '';
        const colorField = document.body.querySelector('.ffb-edit-record-color');
        const sizeField = document.body.querySelector('.ffb-edit-record-size');
        const locationField = document.body.querySelector('.ffb-edit-record-location-name');
        if (edit) {
            ffbUtils.store.editCube = cube;

            colorField.value = cube.color;
            sizeField.value = cube.size;
            locationField.innerText = cube.productionLocation.name;
        } else {
            colorField.value = '';
            locationField.innerText = '';
            sizeField.value = '';
            ffbUtils.store.editCube = null;
        }
    };

    ffbUtils.cube.fillTable = function () {
        ffbUtils.store.cubes.forEach((c) => {
            ffbUtils.store.cubeTable.append(ffbUtils.cube.createRecord(c));
        })
    };

    ffbUtils.cube.cleanTable = function () {
        ffbUtils.store.cubeTable.innerHTML = '';
    };

    ffbUtils.location = {};

    ffbUtils.location.createDropDownElement = function createLocation(name) {
        const location = document.createElement('div');
        location.classList.add('ffb-dropdown-location');
        location.innerText = name;
        return location;
    };

    window.ffbUtils = ffbUtils;
}());
