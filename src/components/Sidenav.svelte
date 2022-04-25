<script>
    import { fly } from "svelte/transition";
    export let show = false;
</script>

{#if show}
    <nav in:fly={{ x:2000, duration: 300 }} out:fly={{ x:3000, duration: 1200 }}>
        <div class="nav-header">
            <button on:click={() => { show = false }}>
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <line x1="18" y1="6" x2="6" y2="18"></line>
                    <line x1="6" y1="6" x2="18" y2="18"></line>
                </svg>
            </button>
        </div>

        <div class="main-section">
            <slot />
        </div>

        <h2> RealDO </h2>
    </nav>
{/if}

<style>
    nav {
        position: fixed;
        top: 0;
        right: 0;
        width: 100%;
        height: 100%;
        background-color: var(--primary-color);
        overflow-y: auto;
        z-index: 9;
    }

    .nav-header {
        height: 64px;
        padding: 0 16px;
        display: flex;
        align-items: center;
        justify-content: flex-end;
    }

    .nav-header button {
        height: 36px;
        width: 36px;
        padding: 0;
        display: flex;
        align-items: center;
        justify-content: center;
        border-radius: 8px;
        border: 2px solid var(--dark-color);
        background-color: var(--light-color);
    }

    .nav-header button:hover {
        transform: translate(4px,-4px);
        box-shadow: -4px 4px 0 var(--dark-color);
    }

    .main-section {
        display: grid;
        gap: 16px;
        padding: 16px;
        margin-top: -14px;
    }

    h2 {
        right: 0;
        left: 0;
        bottom: 16px;
        margin: 0;
        position: absolute;
        text-align: center;
        font-size: 21px;
        color: var(--dark-color);
        font-weight: 700;
        font-family: 'Roboto Mono', monospace;
    }

    @media (min-width: 1025px) and (max-width: 1920px){
        nav {
            width: 300px;
            border-left: 2px solid var(--dark-color);
        }
    }
</style>
