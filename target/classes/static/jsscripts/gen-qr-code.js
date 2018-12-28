'use strict';

const qrcode = new QRCode('qrcode');

function generateQRCode() {
    const urlElement = document.getElementById('url');
    urlElement.value = generateOtpAuthUrl();
    qrcode.makeCode(urlElement.value);
}

function getOptionalField(name) {
  const value = document.getElementById(name).value;
  return value ? '&' + name + '=' + value : '';
}

function generateOtpAuthUrl() {
    // Required fields
    const label = document.getElementById('label').value;
    const user = document.getElementById('user').value;
    const key = document.getElementById('key').value;

    // Optional fields
    const digits = getOptionalField('digits');
    const period = getOptionalField('period');

    return 'otpauth://totp/' + label + ':' + user + '?secret=' + key +
      '&issuer=' + label +
      digits +
      period;
}

generateQRCode();

$("input[type='text']").
  on("blur", function () {
      generateQRCode();
  }).
  on('keydown', function (e) {
    if (e.keyCode == 13) {
      generateQRCode();
    }
  });