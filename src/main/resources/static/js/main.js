document.getElementById('year').textContent = new Date().getFullYear();

const form = document.getElementById('contact-form');
const statusEl = document.getElementById('form-status');
const submitBtn = document.getElementById('submit-btn');

form.addEventListener('submit', async (e) => {
  e.preventDefault();

  const payload = {
    name: document.getElementById('name').value.trim(),
    email: document.getElementById('email').value.trim(),
    message: document.getElementById('message').value.trim(),
  };

  submitBtn.disabled = true;
  statusEl.textContent = 'Sending...';
  statusEl.className = 'form-status';

  try {
    const res = await fetch('/api/contact', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    });

    const data = await res.json();

    if (res.ok) {
      statusEl.textContent = data.message || 'Thanks! Message sent.';
      statusEl.className = 'form-status success';
      form.reset();
    } else {
      const firstError = data.errors ? Object.values(data.errors)[0] : null;
      statusEl.textContent = firstError || 'Something went wrong. Please check your input.';
      statusEl.className = 'form-status error';
    }
  } catch (err) {
    statusEl.textContent = 'Network error - please try again.';
    statusEl.className = 'form-status error';
  } finally {
    submitBtn.disabled = false;
  }
});
