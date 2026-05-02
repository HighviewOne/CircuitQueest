// CircuitQueest — GitHub Pages landing page redesign
// Hero + features + topic showcase + download CTA, all in dark cockpit aesthetic.

const { useState, useEffect } = React;

function LandingPage() {
  return (
    <div className="lp">
      <div className="lp__pcb" aria-hidden="true">
        <svg width="100%" height="100%" preserveAspectRatio="xMidYMid slice">
          <defs>
            <pattern id="lp-grid" width="32" height="32" patternUnits="userSpaceOnUse">
              <circle cx="1" cy="1" r="1" fill="#355397" opacity="0.25" />
            </pattern>
            <radialGradient id="lp-vig" cx="50%" cy="0%" r="80%">
              <stop offset="0%" stopColor="#2480ff" stopOpacity="0.18" />
              <stop offset="50%" stopColor="#0a0e1a" stopOpacity="0" />
            </radialGradient>
          </defs>
          <rect width="100%" height="100%" fill="url(#lp-grid)" />
          <rect width="100%" height="100%" fill="url(#lp-vig)" />
          <g stroke="#355397" strokeWidth="1.2" fill="none" opacity="0.45">
            <path d="M 0 180 H 200 L 240 220 H 480 L 520 200 H 720" />
            <path d="M 0 360 H 120 L 160 320 H 360" />
            <path d="M 480 480 L 520 520 H 720" />
          </g>
          <g fill="#5da3ff" opacity="0.7">
            <circle cx="200" cy="180" r="3" />
            <circle cx="480" cy="220" r="3" />
            <circle cx="120" cy="360" r="3" />
            <circle cx="520" cy="520" r="3" />
          </g>
        </svg>
      </div>

      <header className="lp__nav">
        <div className="lp__brand">
          <div className="cq-brand__mark">
            <svg viewBox="0 0 28 28" width="24" height="24">
              <circle cx="14" cy="14" r="13" fill="none" stroke="#5da3ff" strokeWidth="1.5" />
              <path d="M16 5 L9 16 H14 L12 23 L19 12 H14 Z" fill="#f4b840" />
            </svg>
          </div>
          <span>CircuitQu<i>EE</i>st</span>
        </div>
        <nav className="lp__nav-links">
          <a href="#features">Features</a>
          <a href="#topics">Topics</a>
          <a href="#docs">Docs</a>
          <a href="#" className="lp__nav-cta">Download APK ↓</a>
        </nav>
      </header>

      <section className="lp__hero">
        <div className="lp__hero-text">
          <div className="lp__eyebrow">
            <span className="lp__eyebrow-dot" />
            v1.0 · Production ready · 300+ tests
          </div>
          <h1 className="lp__title">
            Master <span className="lp__title-grad">electrical engineering</span> through quests.
          </h1>
          <p className="lp__sub">
            42 topics. 5-minute lessons. Boss-battle quizzes. Earn XP, unlock advanced material, and
            learn the math that makes electrons behave — offline, on Android.
          </p>
          <div className="lp__cta-row">
            <a className="lp__btn lp__btn--primary" href="#">
              <svg width="20" height="20" viewBox="0 0 20 20" fill="none">
                <path d="M5 4 L5 16 L8 13 L12 17 L14 16 L10 12 L13 9 Z" fill="currentColor" />
              </svg>
              Get on Android
              <span className="lp__btn-meta">free · 12 MB</span>
            </a>
            <a className="lp__btn lp__btn--ghost" href="#">
              <svg width="18" height="18" viewBox="0 0 18 18" fill="currentColor"><path d="M9 0a9 9 0 0 0-2.85 17.54c.45.08.62-.2.62-.43v-1.7c-2.5.55-3.04-1.05-3.04-1.05-.4-1.05-1-1.32-1-1.32-.83-.57.06-.55.06-.55.92.07 1.4.94 1.4.94.82 1.4 2.14 1 2.66.76.08-.6.32-1 .58-1.23-2-.23-4.1-1-4.1-4.46 0-.98.35-1.78.92-2.4-.1-.23-.4-1.15.08-2.4 0 0 .76-.24 2.5.92a8.6 8.6 0 0 1 4.55 0c1.74-1.16 2.5-.92 2.5-.92.5 1.25.18 2.17.1 2.4.57.62.92 1.42.92 2.4 0 3.46-2.1 4.22-4.1 4.45.32.28.62.83.62 1.68v2.5c0 .24.17.52.62.43A9 9 0 0 0 9 0z"/></svg>
              View on GitHub
            </a>
          </div>
          <div className="lp__stats">
            <div className="lp__stat"><div className="lp__stat-num">42</div><div className="lp__stat-lbl">Topics</div></div>
            <div className="lp__stat"><div className="lp__stat-num">300<i>+</i></div><div className="lp__stat-lbl">Tests</div></div>
            <div className="lp__stat"><div className="lp__stat-num">9</div><div className="lp__stat-lbl">Categories</div></div>
            <div className="lp__stat"><div className="lp__stat-num">100%</div><div className="lp__stat-lbl">Offline</div></div>
          </div>
        </div>

        <div className="lp__hero-visual">
          <div className="lp__phone-glow" />
          <div className="lp__phone">
            <div className="lp__phone-notch" />
            <div className="lp__phone-screen">
              <div className="lp__phone-bar">
                <span style={{color:'#5da3ff'}}>CircuitQu<i style={{color:'#fff'}}>EE</i>st</span>
                <span className="lp__phone-xp-label">L4 · 1240 XP</span>
              </div>
              <div className="lp__phone-xp">
                <div className="lp__phone-xp-fill" />
              </div>
              <div className="lp__phone-eyebrow">CHOOSE YOUR QUEST</div>
              <div className="lp__phone-card lp__phone-card--current">
                <div className="lp__phone-glyph lp__phone-glyph--gold">∿</div>
                <div>
                  <div className="lp__phone-title">AC Circuits</div>
                  <div className="lp__phone-sub">Sinusoidal steady-state</div>
                  <div className="lp__phone-status">● In progress</div>
                </div>
              </div>
              <div className="lp__phone-card lp__phone-card--done">
                <div className="lp__phone-glyph">V=IR</div>
                <div>
                  <div className="lp__phone-title">Ohm's Law</div>
                  <div className="lp__phone-sub">7/7 mastered</div>
                </div>
                <div className="lp__phone-check">✓</div>
              </div>
              <div className="lp__phone-card lp__phone-card--locked">
                <div className="lp__phone-glyph">TN</div>
                <div>
                  <div className="lp__phone-title">Thévenin–Norton</div>
                  <div className="lp__phone-sub">Locked</div>
                </div>
              </div>
            </div>
          </div>
          {/* corner readout */}
          <div className="lp__readout">
            <div className="lp__readout-row"><span>V</span><span>9.00 V</span></div>
            <div className="lp__readout-row"><span>I</span><span>0.090 A</span></div>
            <div className="lp__readout-row"><span>P</span><span>0.81 W</span></div>
          </div>
        </div>
      </section>

      <section id="features" className="lp__features">
        <div className="lp__section-eyebrow">§ FEATURES</div>
        <h2 className="lp__section-title">Built for engineers who learn by doing.</h2>
        <div className="lp__feature-grid">
          <Feature
            glyph="⚡"
            title="Quest progression"
            body="Topics unlock sequentially as you master fundamentals. No skipping — the math compounds."
            accent="gold"
          />
          <Feature
            glyph="∑"
            title="Boss-battle quizzes"
            body="Multiple-choice and numeric input with tolerance. Get a wrong answer? See exactly why."
            accent="blue"
          />
          <Feature
            glyph="◐"
            title="42 topics, 9 tracks"
            body="From Ohm's Law to ML hardware. Deep enough for ECE coursework, friendly enough for tinkerers."
            accent="cyan"
          />
          <Feature
            glyph="◌"
            title="Offline-first"
            body="Every lesson, quiz, and progress entry lives on your device. Works on the train, at the bench, anywhere."
            accent="blue"
          />
          <Feature
            glyph="✓"
            title="Production-grade"
            body="MVVM + Hilt + Compose. 50% coverage across 300+ tests. Open source under MIT."
            accent="cyan"
          />
          <Feature
            glyph="↑"
            title="XP & streaks"
            body="+50 XP per lesson, score-based bonuses on quizzes, daily streak multipliers. The reward loop is real."
            accent="gold"
          />
        </div>
      </section>

      <section id="topics" className="lp__topics">
        <div className="lp__section-eyebrow">§ CURRICULUM</div>
        <h2 className="lp__section-title">Nine tracks, forty-two quests.</h2>
        <div className="lp__topic-grid">
          {[
            { name: 'Circuit Fundamentals', count: 6, glyph: 'Ω', topics: ["Ohm's Law", 'Series & Parallel', "Kirchhoff's Laws", 'Capacitors & Inductors', 'AC Circuits', 'Thévenin–Norton'] },
            { name: 'Semiconductor Devices', count: 4, glyph: '◐', topics: ['Diodes', 'BJT Transistors', 'MOSFETs', 'Semiconductor Physics'] },
            { name: 'Analog Design', count: 4, glyph: '∿', topics: ['Op-Amps', 'Filters', 'Analog Circuits', 'Audio Electronics'] },
            { name: 'Digital Systems', count: 4, glyph: '01', topics: ['Digital Logic', 'Digital Systems', 'VLSI', 'ML Hardware'] },
            { name: 'Signals & Control', count: 3, glyph: 'ƒ', topics: ['Signals & Systems', 'Control Systems', 'DSP'] },
            { name: 'Power & Energy', count: 6, glyph: '⚡', topics: ['Transformers', 'Power Electronics', 'Power Systems', 'Renewables', 'Electric Vehicles', 'Battery Storage'] },
            { name: 'RF & Communications', count: 7, glyph: '◌', topics: ['Electromagnetics', 'Transmission Lines', 'Comms', 'Antennas', 'RF Circuits', 'Fiber Optics', 'Radar'] },
            { name: 'Hardware & Embedded', count: 5, glyph: '⌘', topics: ['PCB Design', 'Embedded', 'Sensors', 'Signal Integrity', 'IoT'] },
            { name: 'Specialized', count: 3, glyph: '✚', topics: ['Electric Machines', 'MEMS', 'Biomedical'] },
          ].map(track => (
            <div key={track.name} className="lp__track">
              <div className="lp__track-head">
                <span className="lp__track-glyph">{track.glyph}</span>
                <div>
                  <div className="lp__track-name">{track.name}</div>
                  <div className="lp__track-meta">{track.count} quests</div>
                </div>
              </div>
              <ul className="lp__track-topics">
                {track.topics.map(t => <li key={t}>{t}</li>)}
              </ul>
            </div>
          ))}
        </div>
      </section>

      <section className="lp__cta">
        <div className="lp__cta-card">
          <div className="lp__cta-corners"><i/><i/><i/><i/></div>
          <div className="lp__cta-eyebrow">READY TO START THE QUEST?</div>
          <h2>Download CircuitQu<i>EE</i>st for Android.</h2>
          <p>Free. Open source. No ads, no accounts, no telemetry.</p>
          <div className="lp__cta-row">
            <a className="lp__btn lp__btn--primary" href="#">Get on Android <span className="lp__btn-meta">v1.0 · 12 MB</span></a>
            <a className="lp__btn lp__btn--ghost" href="#">Read the docs</a>
          </div>
        </div>
      </section>

      <footer className="lp__footer">
        <div className="lp__footer-brand">
          <div className="cq-brand__mark" style={{ width: 32, height: 32 }}>
            <svg viewBox="0 0 28 28" width="20" height="20">
              <circle cx="14" cy="14" r="13" fill="none" stroke="#5da3ff" strokeWidth="1.5" />
              <path d="M16 5 L9 16 H14 L12 23 L19 12 H14 Z" fill="#f4b840" />
            </svg>
          </div>
          <span>CircuitQu<i>EE</i>st</span>
        </div>
        <div className="lp__footer-text">MIT licensed · Built for engineers · © 2026 HighviewOne</div>
        <div className="lp__footer-links">
          <a href="#">GitHub</a><a href="#">Privacy</a><a href="#">Issues</a>
        </div>
      </footer>
    </div>
  );
}

function Feature({ glyph, title, body, accent = 'blue' }) {
  return (
    <div className="lp__feature">
      <div className={`lp__feature-glyph lp__feature-glyph--${accent}`}>{glyph}</div>
      <h3>{title}</h3>
      <p>{body}</p>
    </div>
  );
}

Object.assign(window, { LandingPage });
