<script>
    import Loader from "./Loader.svelte";
    import Sidenav from "./Sidenav.svelte";

    let show = false;
    let loading = false;

    const navHeaderButton = () => {
        loading = !loading;

        setTimeout(() => {
            loading = !loading;
            window.location.reload();
        }, 1000);
    };
</script>

<header>
    <h1>
        RealDO
    </h1>
    <button class="menu-btn" on:click={navHeaderButton}>
        <!-- <svg xmlns="http://www.w3.org/2000/svg" width="21" height="21" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <line x1="21" y1="10" x2="7" y2="10"></line>
            <line x1="21" y1="6" x2="3" y2="6"></line>
            <line x1="21" y1="14" x2="3" y2="14"></line><line x1="21" y1="18" x2="7" y2="18"></line>
        </svg> -->
        <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <polyline points="1 4 1 10 7 10"></polyline>
            <polyline points="23 20 23 14 17 14"></polyline>
            <path d="M20.49 9A9 9 0 0 0 5.64 5.64L1 10m22 4l-4.64 4.36A9 9 0 0 1 3.51 15"></path>
        </svg>
    </button>
</header>

<Sidenav bind:show/>

<Loader bind:loading />

<style>
    header {
        height: 54px;
        padding: 0 16px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        border-bottom: 2px solid var(--dark-color);
        background-color: var(--primary-color);
    }

    h1 {
        margin: 0;
        font-size: 24px;
        color: var(--dark-color);
        font-weight: 700;
        font-family: 'Roboto Mono', monospace;
    }

    .menu-btn {
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

    .menu-btn:hover {
        transform: translate(4px,-4px);
        box-shadow: -4px 4px 0 var(--dark-color);
    }
</style>