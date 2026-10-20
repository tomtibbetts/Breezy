# Akamai University International

Proof-of-concept landing site for a fictional online university. The project is static HTML, CSS, and JavaScript. Body copy uses Lorem Ipsum. No content, branding, or photography is taken from the example universities.

## Purpose

Create a university-style public website for **Akamai University International**, using two live institutions as structural references:

- [Saybrook University](https://www.saybrook.edu) — accredited online health-sciences college
- [Wake Forest University](https://www.wfu.edu) — traditional collegiate research university

The goal was not to clone either site. The goal was to combine Saybrook’s online-university information architecture with Wake Forest’s prestige, navigation, and storytelling patterns, then apply an original brand.

## Sources

### Institutional references

Public pages were reviewed in October 2026 to understand section order, navigation labels, and prospective-student journeys.

**Saybrook University** (`saybrook.edu`)

| Pattern observed | How it appears here |
| --- | --- |
| Purpose-led hero (“path to purpose and service”) | Homepage headline and belonging section |
| Accreditation announcement on the homepage | Gold-topped accreditation bar |
| Academic programs as descriptive cards | Program grid on Home and Academics |
| “How we learn together” (virtual, residential, community) | Experience page and homepage learning cards |
| Year-round, stepped application process | Eight-step Admissions process |
| Application requirements (degree, transcripts, statement, interview) | Requirements cards on Admissions |
| Investing in your future / affordability | Tuition and aid section |
| Request-information and counselor contact | Request Info modal and Contact page |

**Wake Forest University** (`wfu.edu`)

| Pattern observed | How it appears here |
| --- | --- |
| Utility bar (Students, Faculty, Apply, Search) | Top utility bar on every page |
| Short primary navigation and persistent Apply CTA | Sticky header with gold Apply button |
| Full-bleed cinematic hero | Homepage and inner-page heroes |
| Motto as a full-width band (*Pro Humanitate*) | *Sapientia Sine Finibus* motto band |
| “By the numbers” outcomes | Stats section (countries, ratio, programs, outcomes) |
| News plus dated events | Homepage news/events and News page |
| Collegiate footer with study, university, and resource columns | Site footer |

Wake Forest brand documentation was also consulted for *how* a university site treats color, contrast, and short nav labels, not for copying the Old Gold / black identity:

- [WFU Color, Patterns and Papers](https://brand.wfu.edu/our-brand-identity/color-patterns-papers/)
- [WFU header/footer and accessibility notes](https://wordpress.wfu.edu/2026/08/10/adjusting-to-the-new-header-footer/)

### What was not copied

- No HTML, CSS, JavaScript, photography, logos, or copy from Saybrook or Wake Forest
- No WFU Old Gold (`#9E7E38`) / black palette
- No Saybrook program names or accreditation language
- Motto, school names, leadership, address, and statistics are original and fictional

### Third-party assets

| Asset | Source | Use |
| --- | --- | --- |
| Display type | [Cormorant Garamond](https://fonts.google.com/specimen/Cormorant+Garamond) via Google Fonts | Headings |
| Interface type | [Manrope](https://fonts.google.com/specimen/Manrope) via Google Fonts | Body, navigation, forms |
| Photography | [Unsplash](https://unsplash.com); Public Health card is stored locally as `assets/images/public-health.jpg` so GitHub Pages does not depend on a hotlink | Heroes, program cards, news, portraits |
| Filler text | Standard Lorem Ipsum | Paragraphs and form placeholders |
| Logo, favicon, wave pattern | Original SVG in `assets/` | Brand mark and decorative texture |

Unsplash images are used under the [Unsplash License](https://unsplash.com/license) for this demonstration. Replace them with licensed institutional photography before any public launch.

## Methods

### 1. Reference review

Public homepages and related admissions, academics, and brand pages were read as content outlines. The review focused on:

- What a prospective student sees first
- How trust is established (accreditation, faculty, outcomes)
- How programs are discovered
- How an online university explains community without a traditional campus
- How a prestige university uses motto, news, events, and statistics

### 2. Information architecture

The site was planned as a small university web with a landing page plus supporting routes, rather than a single long scroll:

```text
Home
  Academics
  Admissions
  Experience
  About
  News
  Contact
```

That map follows common university IA: study, apply, belong, about, stories, inquire.

### 3. Original brand

“Akamai” is used in the Hawaiian sense of skill and wisdom. The visual system is Pacific and academic, not a restyle of either reference:

- Navy `#0B1D2A`, gold `#C4A24A`, teal `#0F6B6B`, cream `#F4EFE4`
- Circular seal with a stylized A, sun mark, and wave
- Motto: *Sapientia Sine Finibus* (Wisdom Without Borders)
- Fictional Honolulu headquarters and global online classroom

### 4. Implementation

The site is hand-authored static files. There is no CMS, build step, framework, or backend.

| Layer | Method |
| --- | --- |
| Structure | Semantic HTML5 on each page (skip link, header, main, footer) |
| Presentation | One shared design system in `css/styles.css` (custom properties, grid, responsive breakpoints at 980px and 640px) |
| Behavior | Vanilla JavaScript in `js/main.js` (sticky header, mobile drawer, search overlay, request-info modal, form acknowledgement, news filters) |
| Identity | Original SVG assets |
| Accessibility | Skip link, focus styles, current-page `aria-current`, reduced-motion support, empty `alt` on decorative marks |

Header, footer, and overlays are repeated on each HTML page so the site works when opened as files or from a simple static server.

Forms prevent default submission and show an on-page thank-you message. They do not post data.

### 5. Verification

Pages and assets were served with Python’s `http.server` and checked for HTTP 200 responses and intact internal links. Desktop, inner-page, and mobile layouts were reviewed with headless Microsoft Edge screenshots. Interactive click-through of the live menu and forms should still be confirmed in a local browser.

## Project structure

```text
AU Website POC/
  index.html            Landing page
  academics.html        Schools and programs
  admissions.html       Application steps, requirements, aid
  experience.html       Learning model and student support
  about.html            Mission, motto, leadership, history
  news.html             Filterable news
  contact.html          Admissions inquiry form
  css/styles.css        Design system and layout
  js/main.js            Interface behavior
  assets/
    logo.svg
    favicon.svg
    pattern.svg
  README.md
```

## How to view

From the project folder:

```bash
python -m http.server 8080
```

Then open [http://127.0.0.1:8080](http://127.0.0.1:8080).

HTML files can also be opened directly in a browser. Google Fonts and Unsplash images require a network connection.

## Status

This is a design and front-end proof of concept. The university, accreditation body, statistics, people, and contact details are fictional. Replace filler text, photography, and demonstration forms before using the site for any real admissions or marketing purpose.
