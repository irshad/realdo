<script>
    import { fly } from "svelte/transition";
    import { createEventDispatcher } from "svelte";

    const dispatch = createEventDispatcher();
    export let showSearch = true;
    export let searchValue = '';

    const search = () => {
        dispatch('search', {
            search: searchValue
        });
    }
</script>

{#if showSearch}
    <div class="main-section" in:fly={{ x:2000, duration: 300 }} out:fly={{ x:3000, duration: 1200 }}>
        <div class="page-inside">
            <div class="search-header">
                <button class="search-modal-close-btn" on:click={() => {(showSearch = !showSearch), (searchValue = '')}}>
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <line x1="18" y1="6" x2="6" y2="18"></line>
                        <line x1="6" y1="6" x2="18" y2="18"></line>
                    </svg>
                </button>
            </div>

            <div class="search-area">
                <input class="br-8" type="search" bind:value={searchValue} placeholder="Add Todo.." on:keyup={search}>

                <slot />
            </div>
        </div>
        <h2> RealDO </h2>
    </div>
{/if}

<svelte:head>
    {#if showSearch}
        <style>html, body {overflow: hidden}</style>
    {/if}
    {#if !showSearch}
        <style>html, body {overflow: hidden}</style>
    {/if}
</svelte:head>

<style>
    .main-section {
        width: 100%;
        height: 100%;
        z-index: 9;
        inset: 0;
        position: absolute;
        background-color: var(--primary-color);
    }

    .page-inside {
        padding: 16px;
    }

    .search-header {
        gap: 16px;
        display: flex;
        align-items: center;
        justify-content: flex-end;
    }

    .search-area {
        width: 90%;
        margin: 0 auto;
    }

    input[type="search"] {
        font-size: 16px;
        padding: 0px 8px 0 8px;
        height: 36px;
        width: 100%;
        border: 2px solid var(--dark-color);
        font-weight: 500;
        font-family: 'Roboto Mono', monospace;
        margin: 40px auto;
    }

    input[type="search"]:focus {
        outline: none;
    }

    .search-modal-close-btn {
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

    .search-modal-close-btn:hover {
        transform: translate(4px,-4px);
        box-shadow: -4px 4px 0 var(--dark-color);
    }

    h2 {
        left: 0;
        right: 0;
        bottom: 16px;
        position: absolute;
        text-align: center;
        font-size: 21px;
        color: var(--dark-color);
        font-weight: 700;
        font-family: 'Roboto Mono', monospace;
    }
</style>
