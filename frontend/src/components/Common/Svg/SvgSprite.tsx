const SvgSprite = () => {
  return (
    <svg xmlns="http://www.w3.org/2000/svg" display="none">
      <symbol id="recipe" viewBox="0 0 22 26">
        <path d="M20.675 12.44c-.324-.604-.643-1.123-.868-1.533a5.058 5.058 0 0 1-.256-.516.808.808 0 0 1-.068-.244v-.005.001a.311.311 0 0 1 .054-.168.344.344 0 0 1 .116-.096.83.83 0 0 1 .135-.06l.096.045.25-.053c.53-.111.904-.529.904-1.011V7.733c0-.813-.743-1.47-1.662-1.471h-2.668c-.23-.425-.717-.72-1.284-.72h-4.035c-.567 0-1.053.296-1.284.72H8.23c-.203-.737-.762-2.216-2.261-4.017A8.064 8.064 0 0 0 4.489.91 5.94 5.94 0 0 0 3.376.287 3.487 3.487 0 0 0 2.051 0 2.454 2.454 0 0 0 .86.297a1.61 1.61 0 0 0-.664.677A1.779 1.779 0 0 0 0 1.801c0 .34.086.68.259.998.172.316.436.61.799.825.818.483 1.589 1.076 2.155 1.67.32.332.564.665.73.968h-.019c-.917 0-1.66.658-1.66 1.47v1.064c0 .487.381.907.915 1.014l.267.053.076-.04a.78.78 0 0 1 .112.049.363.363 0 0 1 .128.1.31.31 0 0 1 .057.173v.001a.77.77 0 0 1-.068.245c-.095.239-.296.596-.544 1.022-.742 1.285-1.904 3.225-1.905 5.645 0 .157.004.316.015.477.13 2.033.929 4.141 2.613 5.762C5.61 24.92 8.183 26.006 11.65 26c3.468.006 6.04-1.08 7.722-2.703 1.684-1.62 2.482-3.73 2.614-5.762.01-.161.014-.32.014-.477 0-1.844-.677-3.409-1.325-4.618zm-1.012-4.707v.866c-.221.026-.471.1-.733.245-.048.026-.093.064-.14.096h-1.937V7.48h2.523c.159 0 .287.113.287.253zM1.82 2.61a.886.886 0 0 1-.324-.342.991.991 0 0 1-.122-.468.7.7 0 0 1 .07-.318.346.346 0 0 1 .14-.151.938.938 0 0 1 .466-.115c.206 0 .464.057.742.173.417.171.864.469 1.238.775.374.304.68.62.831.802.97 1.167 1.5 2.17 1.786 2.87.058.142.104.269.144.385H5.405c-.216-.6-.628-1.168-1.142-1.712-.67-.702-1.525-1.356-2.442-1.899zm1.818 5.986v-.864c0-.14.128-.253.285-.254H9.96v1.46H4.512c-.062-.041-.124-.088-.188-.12a2.03 2.03 0 0 0-.685-.222zm16.974 8.869c-.115 1.804-.83 3.64-2.253 5.006-1.425 1.363-3.554 2.305-6.71 2.31-3.154-.005-5.283-.947-6.709-2.31-1.423-1.365-2.137-3.202-2.251-5.006a6.281 6.281 0 0 1-.014-.408c0-1.575.582-2.958 1.193-4.096.305-.57.615-1.074.862-1.521.123-.225.231-.435.315-.643.082-.208.146-.414.147-.652l-.002-.083a1.497 1.497 0 0 0-.06-.312h4.83v2.925c0 .531.49.962 1.09.962.6 0 1.091-.43 1.091-.962v-.621c0-.531.49-.966 1.09-.966.602 0 1.092.435 1.092.966v1.838c0 .617.564 1.12 1.265 1.12.696 0 1.265-.503 1.265-1.12V9.751h1.318c-.03.1-.052.203-.06.31l-.003.085c.002.239.065.444.148.652.147.362.367.74.616 1.171.749 1.286 1.756 3.022 1.753 5.09 0 .134-.003.27-.012.407z" />
      </symbol>
      <symbol id="list" viewBox="0 0 20 18">
        <path stroke="#6B6B6B" strokeWidth="2" d="M0 9h18.945M0 1h18.945M0 17h18.945" />
      </symbol>
      <symbol id="profile" viewBox="0 0 20 20">
        <path d="M5.5 5.042A4.665 4.665 0 0 1 10.167.375a4.665 4.665 0 0 1 4.666 4.667 4.665 4.665 0 0 1-4.666 4.666A4.665 4.665 0 0 1 5.5 5.042zM.833 16.708c0-3.103 6.219-4.666 9.334-4.666 3.115 0 9.333 1.563 9.333 4.666v2.334H.833v-2.334z" />
      </symbol>
      <symbol id="search" viewBox="0 0 22 22">
        <path d="M15.723 13.836h-.993l-.353-.34a8.14 8.14 0 0 0 1.975-5.32A8.176 8.176 0 0 0 8.176 0 8.176 8.176 0 0 0 0 8.176a8.176 8.176 0 0 0 8.176 8.176 8.14 8.14 0 0 0 5.32-1.975l.34.353v.993L20.127 22 22 20.126l-6.277-6.29zm-7.547 0a5.653 5.653 0 0 1-5.66-5.66 5.653 5.653 0 0 1 5.66-5.66 5.653 5.653 0 0 1 5.66 5.66 5.653 5.653 0 0 1-5.66 5.66z" />
      </symbol>
      <symbol id="arrow" viewBox="0 0 8 12">
        <path d="M7.41 10.59L2.83 6l4.58-4.59L6 0 0 6l6 6 1.41-1.41z" />
      </symbol>
      <symbol id="bookmark" viewBox="0 0 24 24">
        <g clipPath="url(#a)">
          <path d="M17 3H7c-1.1 0-2 .9-2 2v16l7-3 7 3V5c0-1.1-.9-2-2-2zm0 15l-5-2.18L7 18V5h10v13z" />
        </g>
        <defs>
          <clipPath id="a">
            <path d="M0 0h24v24H0z" />
          </clipPath>
        </defs>
      </symbol>
      <symbol id="review" viewBox="0 0 14 14">
        <g clipPath="url(#a)">
          <path d="M11.667 1.167H2.333a1.17 1.17 0 0 0-1.166 1.166v10.5L3.5 10.5h8.167a1.17 1.17 0 0 0 1.166-1.167v-7a1.17 1.17 0 0 0-1.166-1.166zm0 8.166H3.016l-.683.683V2.333h9.334v7zM4.083 5.25H5.25v1.167H4.083V5.25zm4.667 0h1.167v1.167H8.75V5.25zm-2.333 0h1.166v1.167H6.417V5.25z" />
        </g>
        <defs>
          <clipPath id="a">
            <path d="M0 0h14v14H0z" />
          </clipPath>
        </defs>
      </symbol>
      <symbol id="star" viewBox="0 0 24 24">
        <path d="M17.562 21.56c-.162 0-.321-.04-.465-.115L12 18.765l-5.097 2.68a1 1 0 0 1-1.451-1.054l.973-5.676-4.123-4.02a1 1 0 0 1 .554-1.705l5.699-.828 2.548-5.164a1.042 1.042 0 0 1 1.794 0l2.548 5.164 5.699.828a1 1 0 0 1 .554 1.706l-4.123 4.019.973 5.675a1 1 0 0 1-.986 1.17z" />
        <path d="M21.951 9.67a1 1 0 0 0-.807-.68l-5.699-.828-2.548-5.164A.979.979 0 0 0 12 2.486v16.28l5.097 2.679a1 1 0 0 0 1.451-1.054l-.973-5.676 4.123-4.02a1 1 0 0 0 .253-1.025z" />
      </symbol>
      <symbol id="sort" viewBox="0 0 10 13">
        <path d="M6.39 7.812l-.014 4.205 1.416.004.014-4.205 2.126.006L7.106 5.42 4.264 7.806l2.126.006zm-.697-3.589l-2.125-.006.014-4.205L2.165.01l-.014 4.205-2.125-.006 2.826 2.4 2.841-2.385z" />
      </symbol>
      <symbol id="favorite" viewBox="0 0 15 14">
        <path d="M7.5 13.762l-1.088-.99C2.55 9.27 0 6.96 0 4.126A4.085 4.085 0 0 1 4.125 0C5.43 0 6.683.608 7.5 1.567A4.491 4.491 0 0 1 10.875 0 4.085 4.085 0 0 1 15 4.125c0 2.835-2.55 5.145-6.412 8.655l-1.088.982z" />
      </symbol>
    </svg>
  );
};

export default SvgSprite;