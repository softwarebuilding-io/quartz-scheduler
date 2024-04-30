document.addEventListener("DOMContentLoaded", function () {
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(function (alert) {
        setTimeout(function () {
            alert.style.display = 'none';
        }, 1000);
    });
    flatpickr("#startTime", {
        enableTime: true,
        noCalendar: true,
        dateFormat: "H:i",
    });

    function toggleCronSettings() {
        const cronEnabled = document.getElementById('cronEnabled').value === 'true';
        const cronExpression = document.getElementById('cronExpressionGroup');
        const startTime = document.getElementById('startTimeGroup');
        const repeatInterval = document.getElementById('repeatIntervalGroup');
        const repeatCount = document.getElementById('repeatCountGroup');

        if (cronEnabled) {
            cronExpression.style.display = 'block';
            startTime.style.display = 'none';
            repeatInterval.style.display = 'none';
            repeatCount.style.display = 'none';

            startTime.value = '';
            repeatInterval.value = '';
            repeatCount.value = '';

        } else {
            cronExpression.style.display = 'none';
            startTime.style.display = 'block';
            repeatInterval.style.display = 'block';
            repeatCount.style.display = 'block';
        }

        clearFields(cronEnabled);
    }

    function clearFields(cronEnable) {
        const cronExpression = document.getElementById('cronExpression');
        const startTime = document.getElementById('startTime');
        const repeatInterval = document.getElementById('repeatInterval');
        const repeatCount = document.getElementById('repeatCount');

        if (cronEnable) {
            startTime.value = '';
            repeatInterval.value = '';
            repeatCount.value = '';
        } else {
            cronExpression.value = '';
        }
    }

    const cronEnabledElement = document.getElementById('cronEnabled');

    if (cronEnabledElement) {
        document.getElementById('cronEnabled').addEventListener('change', toggleCronSettings);
        toggleCronSettings();

    }
});

(function () {
    'use strict';
    window.addEventListener('load', function () {
        const forms = document.getElementsByClassName('needs-validation');
        Array.prototype.filter.call(forms, function (form) {
            form.addEventListener('submit', function (event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    }, false);
})();