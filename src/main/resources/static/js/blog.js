const TAG_PALETTE = [
  ['#3457d5', '#6c8bff'],
  ['#0f9d58', '#34c777'],
  ['#d97706', '#f6ad55'],
  ['#c2410c', '#fb923c'],
  ['#7c3aed', '#a78bfa'],
  ['#be185d', '#f472b6'],
];

function gradientFor(seed) {
  let hash = 0;
  for (let i = 0; i < seed.length; i++) hash = seed.charCodeAt(i) + ((hash << 5) - hash);
  const [from, to] = TAG_PALETTE[Math.abs(hash) % TAG_PALETTE.length];
  return `linear-gradient(135deg, ${from}, ${to})`;
}

function formatDate(dateStr) {
  const d = new Date(dateStr + 'T00:00:00');
  return d.toLocaleDateString('en-US', { month: 'short', day: 'numeric', year: 'numeric' });
}

async function fetchPosts(tag) {
  const url = tag ? `/api/blogs?tag=${encodeURIComponent(tag)}` : '/api/blogs';
  const res = await fetch(url);
  if (!res.ok) throw new Error('Failed to load posts');
  return res.json();
}

async function fetchPost(slug) {
  const res = await fetch(`/api/blogs/${encodeURIComponent(slug)}`);
  if (!res.ok) return null;
  return res.json();
}

function tagPillsHtml(tags) {
  return tags.map(t => `<span class="tag-pill">${escapeHtml(t)}</span>`).join('');
}

function escapeHtml(str) {
  const div = document.createElement('div');
  div.textContent = str;
  return div.innerHTML;
}

function blogCard(post) {
  const card = document.createElement('a');
  card.className = 'blog-card';
  card.href = `/post.html?slug=${encodeURIComponent(post.slug)}`;

  card.innerHTML = `
    <div class="blog-card-cover" style="background:${gradientFor(post.tags[0] || post.slug)}">
      <span>${post.coverEmoji || '\u{1F4DD}'}</span>
    </div>
    <div class="blog-card-body">
      <div class="blog-card-tags">${tagPillsHtml(post.tags)}</div>
      <h3>${escapeHtml(post.title)}</h3>
      <p class="blog-card-excerpt">${escapeHtml(post.excerpt)}</p>
      <div class="blog-card-meta">
        <span class="avatar">${escapeHtml(post.authorInitials || '?')}</span>
        <span class="blog-card-meta-text">
          ${escapeHtml(post.author)} &middot; ${formatDate(post.publishedAt)} &middot; ${post.readTimeMinutes} min read
        </span>
      </div>
    </div>
  `;
  return card;
}

function renderGrid(container, posts, emptyMessage) {
  container.innerHTML = '';
  if (!posts.length) {
    container.innerHTML = `<p class="blog-empty">${emptyMessage}</p>`;
    return;
  }
  posts.forEach(post => container.appendChild(blogCard(post)));
}

async function initHomePreview() {
  const grid = document.getElementById('home-blog-grid');
  if (!grid) return;
  const limit = parseInt(grid.dataset.limit || '3', 10);
  try {
    const posts = await fetchPosts();
    renderGrid(grid, posts.slice(0, limit), 'New articles are on the way - check back soon.');
  } catch (e) {
    grid.innerHTML = '<p class="blog-empty">Couldn\'t load articles right now.</p>';
  }
}

async function initBlogPage() {
  const grid = document.getElementById('blog-grid');
  if (!grid) return;
  const tagFilter = document.getElementById('tag-filter');

  let allPosts = [];
  try {
    allPosts = await fetchPosts();
  } catch (e) {
    grid.innerHTML = '<p class="blog-empty">Couldn\'t load articles right now.</p>';
    return;
  }

  renderGrid(grid, allPosts, 'New articles are on the way - check back soon.');

  if (tagFilter && allPosts.length) {
    const tags = [...new Set(allPosts.flatMap(p => p.tags))].sort();
    const allBtn = document.createElement('button');
    allBtn.className = 'tag-filter-btn active';
    allBtn.textContent = 'All';
    allBtn.addEventListener('click', () => {
      setActiveFilter(allBtn);
      renderGrid(grid, allPosts, 'New articles are on the way - check back soon.');
    });
    tagFilter.appendChild(allBtn);

    tags.forEach(tag => {
      const btn = document.createElement('button');
      btn.className = 'tag-filter-btn';
      btn.textContent = tag;
      btn.addEventListener('click', () => {
        setActiveFilter(btn);
        renderGrid(grid, allPosts.filter(p => p.tags.includes(tag)), `No articles tagged "${tag}" yet.`);
      });
      tagFilter.appendChild(btn);
    });
  }

  function setActiveFilter(activeBtn) {
    tagFilter.querySelectorAll('.tag-filter-btn').forEach(b => b.classList.remove('active'));
    activeBtn.classList.add('active');
  }
}

async function initPostPage() {
  const container = document.getElementById('article-container');
  if (!container) return;

  const slug = new URLSearchParams(window.location.search).get('slug');
  if (!slug) {
    container.innerHTML = '<p class="blog-empty">No article specified.</p>';
    return;
  }

  const post = await fetchPost(slug);
  if (!post) {
    container.innerHTML = `
      <p class="blog-empty">This article couldn't be found.</p>
      <a href="/blog.html" class="link-arrow">&larr; Back to all articles</a>
    `;
    return;
  }

  document.title = `${post.title} — Shariq`;

  container.innerHTML = `
    <a href="/blog.html" class="link-arrow back-link">&larr; Back to all articles</a>
    <div class="article-cover" style="background:${gradientFor(post.tags[0] || post.slug)}">
      <span>${post.coverEmoji || '\u{1F4DD}'}</span>
    </div>
    <div class="article-tags">${tagPillsHtml(post.tags)}</div>
    <h1 class="article-title">${escapeHtml(post.title)}</h1>
    <div class="article-meta">
      <span class="avatar avatar-lg">${escapeHtml(post.authorInitials || '?')}</span>
      <div>
        <div class="article-meta-author">${escapeHtml(post.author)}</div>
        <div class="article-meta-sub">${formatDate(post.publishedAt)} &middot; ${post.readTimeMinutes} min read</div>
      </div>
    </div>
    <div class="article-body">${post.content}</div>
  `;
}

document.addEventListener('DOMContentLoaded', () => {
  initHomePreview();
  initBlogPage();
  initPostPage();
});
